package org.rapidoid.db;

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

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.db.model.IPost;
import org.testng.annotations.Test;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class DbInterfaceEqualityTest extends DbTestCommons {

	@Test
	public void testEntityEquality() {
		eq(DB.entity(IPost.class, "id", 0L), DB.entity(IPost.class, "id", 0));
		eq(DB.entity(IPost.class, "id", 123L), DB.entity(IPost.class, "id", 123));
		neq(DB.entity(IPost.class, "id", 12345L), DB.entity(IPost.class));
		neq(DB.entity(IPost.class), DB.entity(IPost.class, "id", 5432));
		neq(DB.entity(IPost.class), DB.entity(IPost.class));
	}

}
