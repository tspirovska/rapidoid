package org.rapidoid.db;

/*
 * #%L
 * rapidoid-db-inmem
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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.rapidoid.lambda.Predicate;
import org.rapidoid.lambda.V1;
import org.rapidoid.util.JSON;
import org.rapidoid.util.U;

class Rec {
	final Class<?> type;
	final String json;

	public Rec(Class<?> type, String json) {
		this.type = type;
		this.json = json;
	}
}

public class DbImpl implements Db {

	private final AtomicLong ids = new AtomicLong();

	private final Map<Long, Rec> data = new ConcurrentHashMap<Long, Rec>(1000);

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private static Rec rec(Object record) {
		return new Rec(record.getClass(), JSON.stringify(record));
	}

	@SuppressWarnings("unchecked")
	private static <T> T obj(Rec rec) {
		return (T) JSON.parse(rec.json, rec.type);
	}

	@SuppressWarnings("unchecked")
	private static <T> T setId(T record, long id) {
		if (record != null) {

			if (record instanceof Map) {
				((Map<Object, Object>) record).put("id", id);
			}

			try {
				U.setId(record, id);
			} catch (Exception e) {
				// ignore
			}
		}

		return record;
	}

	@Override
	public long insert(Object record) {
		sharedLock();
		try {
			long id = ids.incrementAndGet();
			data.put(id, rec(record));
			setId(record, id);
			return id;
		} finally {
			sharedUnlock();
		}
	}

	@Override
	public void delete(long id) {
		sharedLock();
		try {
			data.remove(id);
		} finally {
			sharedUnlock();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E get(long id) {
		sharedLock();
		try {
			Rec rec = data.get(id);
			return (E) (rec != null ? setId(obj(rec), id) : null);
		} finally {
			sharedUnlock();
		}
	}

	@Override
	public <E> E get(long id, Class<E> clazz) {
		sharedLock();
		try {
			return get_(id, clazz);
		} finally {
			sharedUnlock();
		}
	}

	@Override
	public void update(long id, Object record) {
		sharedLock();
		try {
			data.put(id, rec(record));
			setId(record, id);
		} finally {
			sharedUnlock();
		}
	}

	@Override
	public void update(Object record) {
		update(U.getId(record), record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(long id, String column) {
		sharedLock();
		try {
			Map<String, Object> map = get_(id, Map.class);
			return (T) (map != null ? map.get(column) : null);
		} finally {
			sharedUnlock();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> find(final Predicate<E> match) {
		final List<E> results = U.list();

		sharedLock();
		try {

			for (Entry<Long, Rec> entry : data.entrySet()) {
				E record = obj(entry.getValue());
				setId(record, entry.getKey());

				try {
					if (match.eval(record)) {
						results.add(record);
					}
				} catch (ClassCastException e) {
					// ignore, cast exceptions are expected
				} catch (Exception e) {
					throw U.rte(e);
				}
			}

			return results;
		} finally {
			sharedUnlock();
		}
	}

	@Override
	public <E> void each(final V1<E> lambda) {
		sharedLock();
		try {

			for (Entry<Long, Rec> entry : data.entrySet()) {
				E record = obj(entry.getValue());
				setId(record, entry.getKey());

				try {
					lambda.execute(record);
				} catch (ClassCastException e) {
					// ignore, cast exceptions are expected
				} catch (Exception e) {
					throw U.rte(e);
				}
			}
		} finally {
			sharedUnlock();
		}
	}

	@Override
	public void transaction(Runnable transaction) {
		globalLock();
		try {
			transaction.run();
		} finally {
			globalUnlock();
		}
	}

	private <T> T get_(long id, Class<T> clazz) {
		Rec rec = data.get(id);
		return rec != null ? setId(JSON.parse(rec.json, clazz), id) : null;
	}

	private void sharedLock() {
		lock.readLock().lock();
	}

	private void sharedUnlock() {
		lock.readLock().unlock();
	}

	private void globalLock() {
		lock.writeLock().lock();
	}

	private void globalUnlock() {
		lock.writeLock().unlock();
	}

}