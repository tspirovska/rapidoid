package org.rapidoid.model;

/*
 * #%L
 * rapidoid-model
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski
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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.beany.BeanProperties;
import org.rapidoid.beany.Beany;
import org.rapidoid.beany.Prop;
import org.rapidoid.model.impl.BeanItem;
import org.rapidoid.model.impl.BeanListItems;
import org.rapidoid.model.impl.BeanProperty;
import org.rapidoid.model.impl.ListItems;
import org.rapidoid.model.impl.MapItem;
import org.rapidoid.util.Cls;
import org.rapidoid.util.U;

@SuppressWarnings("serial")
@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class Models {

	public static <T> Items beanItems(Class<T> beanType, Object... beans) {
		ListItems items = new BeanListItems<T>(beanType);

		for (Object bean : beans) {
			items.add(item(bean));
		}

		return items;
	}

	@SuppressWarnings("unchecked")
	public static <T> Items beanItemsInfer(T... items) {
		U.must(items.length > 0, "Must have at least 1 item to infer the bean type!");
		Class<T> type = (Class<T>) items[0].getClass();
		return beanItems(type, items);
	}

	public static Item item(Object value) {
		return value instanceof Map ? new MapItem((Map<?, ?>) value) : new BeanItem(value);
	}

	public static Property propertyOf(Class<?> beanType, String property) {
		BeanProperties props = Beany.propertiesOf(beanType);

		if (beanType == Object.class) {
			return new BeanProperty(property, property.equals("id") ? long.class : String.class, null, null);
		}

		Prop prop = props.get(property);
		U.must(prop != null, "Cannot find property %s in class %s!", property, beanType);

		return prop(prop);
	}

	public static List<Property> propertiesOf(Object target, String... propertyNames) {
		return properties(Beany.propertiesOf(target).select(new ModelPropertySelector() {
			@Override
			public boolean eval(Prop prop) throws Exception {
				return true;
			}
		}));
	}

	public static List<Property> editablePropertiesOf(Object target, String... propertyNames) {
		return properties(Beany.propertiesOf(target).select(new ModelPropertySelector(propertyNames) {
			@Override
			public boolean eval(Prop prop) throws Exception {
				return isEditable(prop);
			}
		}));
	}

	public static List<Property> readablePropertiesOf(Object target, String... propertyNames) {
		return properties(Beany.propertiesOf(target).select(new ModelPropertySelector(propertyNames) {
			@Override
			public boolean eval(Prop prop) throws Exception {
				return isReadable(prop);
			}
		}));
	}

	@SuppressWarnings("unchecked")
	private static List<Property> properties(BeanProperties props) {
		String key = Property.class.getCanonicalName();

		List<Property> properties = (List<Property>) props.extras.get(key);

		if (properties == null) {
			properties = U.list();
			for (Prop prop : props) {
				properties.add(prop(prop));
			}
			props.extras.put(key, properties);
		}

		return properties;
	}

	private static BeanProperty prop(Prop prop) {
		return new BeanProperty(prop.getName(), prop.getType(), prop.getGenericType(), prop.getAnnotations());
	}

	public static boolean isEditable(Prop prop) {
		String name = prop.getName();

		if (name.equalsIgnoreCase("id") || name.equalsIgnoreCase("version")) {
			return false;
		}

		if (prop.isReadOnly() && !Collection.class.isAssignableFrom(prop.getType())) {
			return false;
		}

		return true;
	}

	public static boolean isReadable(Prop prop) {
		String name = prop.getName();

		if (name.equalsIgnoreCase("id") || name.equalsIgnoreCase("version")) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public static <T> Items mockBeanItems(int size, Class<T> beanType) {
		T[] beans = (T[]) new Object[size];

		for (int i = 0; i < beans.length; i++) {
			beans[i] = Cls.newInstance(beanType);
			Beany.setId(beans[i], i);
		}

		return beanItems(beanType, beans);
	}

}
