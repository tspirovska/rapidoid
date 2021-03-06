package org.rapidoid.db.impl.inmem;

/*
 * #%L
 * rapidoid-db-inmem
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.beany.Beany;
import org.rapidoid.beany.Prop;
import org.rapidoid.db.Database;
import org.rapidoid.db.DbList;
import org.rapidoid.db.DbRef;
import org.rapidoid.db.DbSet;
import org.rapidoid.db.impl.DbHelper;
import org.rapidoid.db.impl.DbRelationInternals;
import org.rapidoid.inmem.EntitySerializer;
import org.rapidoid.log.Log;
import org.rapidoid.util.U;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class JacksonEntitySerializer implements EntitySerializer {

	private final Database db;

	private final ObjectMapper mapper = new ObjectMapper();

	public JacksonEntitySerializer(Database db) {
		this.db = db;
		initDbMapper();
	}

	@SuppressWarnings("rawtypes")
	private void initDbMapper() {
		SimpleModule dbModule = new SimpleModule("DbModule", new Version(1, 0, 0, null, null, null));

		dbModule.addDeserializer(DbList.class, new JsonDeserializer<DbList>() {
			@SuppressWarnings("unchecked")
			@Override
			public DbList deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
					JsonProcessingException {
				Map<String, Object> data = jp.readValueAs(Map.class);
				String relation = (String) data.get("relation");
				List<? extends Number> ids = (List<Number>) data.get("ids");
				return new InMemDbList(db, null, relation, ids);
			}
		});

		dbModule.addDeserializer(DbSet.class, new JsonDeserializer<DbSet>() {
			@SuppressWarnings("unchecked")
			@Override
			public DbSet deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
					JsonProcessingException {
				Map<String, Object> data = jp.readValueAs(Map.class);
				String relation = (String) data.get("relation");
				List<? extends Number> ids = (List<Number>) data.get("ids");
				return new InMemDbSet(db, null, relation, ids);
			}
		});

		dbModule.addDeserializer(DbRef.class, new JsonDeserializer<DbRef>() {
			@SuppressWarnings("unchecked")
			@Override
			public DbRef deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
					JsonProcessingException {
				Map<String, Object> data = jp.readValueAs(Map.class);
				String relation = (String) data.get("relation");
				List<? extends Number> ids = (List<Number>) data.get("ids");
				U.must(ids.size() <= 1, "Expected 0 or 1 IDs!");
				long id = !ids.isEmpty() ? ids.get(0).longValue() : -1;
				return new InMemDbRef(db, null, relation, id);
			}
		});

		mapper.registerModule(dbModule);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public String stringify(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param extras
	 *            extra JSON attributes in format (key1, value1, key2, value2...)
	 */
	private byte[] stringifyWithExtras(Object value, Object... extras) {
		if (extras.length % 2 != 0) {
			throw new IllegalArgumentException(
					"Expected even number of extras (key1, value1, key2, value2...), but found: " + extras.length);
		}

		try {
			JsonNode node = mapper.valueToTree(value);

			if (!(node instanceof ObjectNode)) {
				throw new RuntimeException("Cannot add extra attributes on a non-object value: " + value);
			}

			ObjectNode obj = (ObjectNode) node;

			int extrasN = extras.length / 2;
			for (int i = 0; i < extrasN; i++) {
				Object key = extras[2 * i];
				if (key instanceof String) {
					obj.put((String) key, String.valueOf(extras[2 * i + 1]));
				} else {
					throw new RuntimeException("Expected extra key of type String, but found: " + key);
				}
			}

			return mapper.writeValueAsBytes(node);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> void parse(byte[] bytes, T destination) {
		try {
			Map<String, Object> map = mapper.readValue(bytes, Map.class);
			Beany.update(destination, map, false);
		} catch (Exception e) {
			Log.error("Cannot parse JSON!", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] serialize(Object entity) {
		Class<?> entityType = db.schema().getEntityTypeFor(entity.getClass());
		return stringifyWithExtras(Beany.serialize(entity), "_class", entityType.getCanonicalName());
	}

	@Override
	public <T> void deserialize(byte[] bytes, T destination) {
		parse(bytes, destination);

		for (Prop prop : Beany.propertiesOf(destination).select(DbHelper.DB_REL_PROPS)) {
			DbRelationInternals rel = prop.get(destination);
			U.notNull(rel, prop.getName());
			rel.setHolder(destination);
		}
	}

}
