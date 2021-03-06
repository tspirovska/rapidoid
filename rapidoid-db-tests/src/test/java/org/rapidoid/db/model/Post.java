package org.rapidoid.db.model;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.db.AbstractEntity;
import org.rapidoid.db.DB;
import org.rapidoid.db.DbRef;
import org.rapidoid.db.DbSet;

/*
 * #%L
 * rapidoid-db-tests
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

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class Post extends AbstractEntity {

	private static final long serialVersionUID = 3776106825040831514L;

	public String content;

	public final DbRef<Profile> postedOn = DB.ref(this, "^posted");

	public final DbSet<Person> likes = DB.set(this, "likes");

	public Post() {
	}

	public Post(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Post [content=" + content + "]";
	}

}
