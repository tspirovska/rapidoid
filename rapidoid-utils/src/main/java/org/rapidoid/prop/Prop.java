package org.rapidoid.prop;

/*
 * #%L
 * rapidoid-utils
 * %%
 * Copyright (C) 2014 Nikolche Mihajlovski
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.rapidoid.util.Cls;
import org.rapidoid.util.TypeKind;
import org.rapidoid.util.U;

public class Prop {

	private final String name;

	private Field field;

	private Method getter;

	private Method setter;

	private Class<?> declaringType;

	private Class<?> type;

	private TypeKind typeKind;

	private Object defaultValue;

	private boolean readOnly = true;

	private ParameterizedType genericType;

	public Prop(String name) {
		this.name = name;
	}

	public Prop(String name, Field field, boolean readOnly) {
		this.name = name;
		this.field = field;
		this.readOnly = readOnly;
	}

	public void init() {
		U.must(field != null || getter != null, "Invalid property: %s", name);

		// TODO: improve inference from getter and setter
		type = field != null ? field.getType() : getter.getReturnType();
		typeKind = Cls.kindOf(type);

		Type gType = field != null ? field.getGenericType() : getter.getGenericReturnType();
		genericType = (gType instanceof ParameterizedType) ? ((ParameterizedType) gType) : null;

		declaringType = field != null ? field.getDeclaringClass() : getter.getDeclaringClass();
	}

	public void setGetter(Method getter) {
		this.getter = getter;
	}

	public void setSetter(Method setter) {
		this.setter = setter;
	}

	public Method getGetter() {
		return getter;
	}

	public Method getSetter() {
		return setter;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object obj) {
		try {
			if (field != null) {
				field.setAccessible(true);
				return (T) field.get(obj);
			} else {
				getter.setAccessible(true);
				return (T) getter.invoke(obj);
			}
		} catch (Exception e) {
			throw U.rte(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object obj, T defaultValue) {
		try {
			if (field != null) {
				field.setAccessible(true);
				return (T) field.get(obj);
			} else {
				getter.setAccessible(true);
				return (T) getter.invoke(obj);
			}
		} catch (Exception e) {
			throw U.rte(e);
		}
	}

	public void set(Object obj, Object value) {
		U.must(!isReadOnly(), "Cannot assign value to a read-only property: %s", name);
		try {
			if (field != null) {
				field.setAccessible(true);
				field.set(obj, convert(value, getType()));
			} else {
				setter.setAccessible(true);
				setter.invoke(obj, convert(value, getType()));
			}
		} catch (Exception e) {
			throw U.rte(e);
		}
	}

	private Object convert(Object value, Class<?> toType) {
		if (value == null || toType.isAssignableFrom(value.getClass())) {
			return value;
		}

		if (toType.equals(String.class)) {
			return String.valueOf(value);
		}

		if (value instanceof String) {
			return Cls.convert((String) value, toType);
		}

		return value;
	}

	public Class<?> getType() {
		return type;
	}

	public TypeKind getTypeKind() {
		return typeKind;
	}

	public ParameterizedType getGenericType() {
		return genericType;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return declaringType.getSimpleName() + "#" + name + ":" + type.getSimpleName();
	}

	public int typeArgsCount() {
		return genericType != null ? genericType.getActualTypeArguments().length : 0;
	}

	public Class<?> typeArg(int index) {
		return Cls.clazz(genericType.getActualTypeArguments()[index]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((declaringType == null) ? 0 : declaringType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prop other = (Prop) obj;
		if (declaringType == null) {
			if (other.declaringType != null)
				return false;
		} else if (!declaringType.equals(other.declaringType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}