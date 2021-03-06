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

import java.util.LinkedHashSet;
import java.util.List;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.db.Database;
import org.rapidoid.db.DbSet;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class DefaultDbSet<E> extends DefaultDbCollection<E> implements DbSet<E> {

	public DefaultDbSet(Database db, Object holder, String relation) {
		super(db, holder, relation, new LinkedHashSet<Long>());
	}

	public DefaultDbSet(Database db, Object holder, String relation, List<? extends Number> ids) {
		this(db, holder, relation);
		initIds(ids);
	}

}
