package org.rapidoid.db.impl;

/*
 * #%L
 * rapidoid-db-impl
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

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentMap;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Relation;
import org.rapidoid.annotation.Since;
import org.rapidoid.beany.Beany;
import org.rapidoid.beany.Metadata;
import org.rapidoid.db.DB;
import org.rapidoid.db.DbColumn;
import org.rapidoid.db.DbList;
import org.rapidoid.db.DbRef;
import org.rapidoid.db.DbSet;
import org.rapidoid.db.Entity;
import org.rapidoid.util.Cls;
import org.rapidoid.util.U;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class EntityImpl implements Entity {

	private static final long serialVersionUID = -5556123216690345146L;

	@SuppressWarnings("unused")
	private final Class<?> type;

	private final ConcurrentMap<String, Object> values;

	private final ConcurrentMap<String, DbColumn<?>> columns = U.concurrentMap();

	private final ConcurrentMap<String, DbSet<?>> sets = U.concurrentMap();

	private final ConcurrentMap<String, DbList<?>> lists = U.concurrentMap();

	private final ConcurrentMap<String, DbRef<?>> refs = U.concurrentMap();

	private final DbColumn<Long> idColumn;

	private final DbColumn<Long> versionColumn;

	private Entity proxy;

	public EntityImpl(Class<?> type, ConcurrentMap<String, Object> values) {
		this.type = type;
		this.values = values;
		this.idColumn = DB.column(values, "id", Long.class);
		this.versionColumn = DB.column(values, "version", Long.class);
	}

	public void setProxy(Entity proxy) {
		this.proxy = proxy;
	}

	@Override
	public String toString() {
		return Beany.beanToStr(proxy, false);
	}

	private long getId() {
		Object id = values.get("id");
		return id != null ? ((Number) id).longValue() : 0;
	}

	public DbColumn<?> column(Method method) {
		U.must(DbColumn.class.isAssignableFrom(method.getReturnType()));

		String name = method.getName();
		DbColumn<?> res = columns.get(name);

		if (res == null) {
			Class<Object> colType = Cls.clazz(Cls.generic(method.getGenericReturnType()).getActualTypeArguments()[0]);
			DbColumn<Object> value = DB.column(values, method.getName(), colType);
			DbColumn<?> old = columns.putIfAbsent(name, value);
			return U.or(old, value);
		}

		return res;
	}

	public DbSet<?> set(Method method) {
		String name = method.getName();
		DbSet<?> res = sets.get(name);

		if (res == null) {
			DbSet<Object> value = DB.set(proxy, rel(method).value());
			DbSet<?> old = sets.putIfAbsent(name, value);
			return old != null ? old : value;
		}

		return res;
	}

	public DbList<?> list(Method method) {
		String name = method.getName();
		DbList<?> res = lists.get(name);

		if (res == null) {
			DbList<Object> value = DB.list(proxy, rel(method).value());
			DbList<?> old = lists.putIfAbsent(name, value);
			return old != null ? old : value;
		}

		return res;
	}

	public DbRef<?> ref(Method method) {
		String name = method.getName();
		DbRef<?> res = refs.get(name);

		if (res == null) {
			DbRef<Object> value = DB.ref(proxy, rel(method).value());
			DbRef<?> old = refs.putIfAbsent(name, value);
			return old != null ? old : value;
		}

		return res;
	}

	private static Relation rel(Method method) {
		Relation rel = Metadata.get(method.getAnnotations(), Relation.class);
		U.must(rel != null, "@Relation is required for method: %s", method);
		return rel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (getId() ^ (getId() >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Entity))
			return false;
		Entity other = (Entity) obj;
		if (id().get() == null || other.id().get() == null) {
			return false;
		}
		if (!id().get().equals(other.id().get()))
			return false;
		return true;
	}

	@Override
	public DbColumn<Long> id() {
		return idColumn;
	}

	@Override
	public DbColumn<Long> version() {
		return versionColumn;
	}

}
