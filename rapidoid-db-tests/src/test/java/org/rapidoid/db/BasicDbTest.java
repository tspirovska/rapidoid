package org.rapidoid.db;

/*
 * #%L
 * rapidoid-db-tests
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

import org.rapidoid.db.model.Person;
import org.rapidoid.lambda.Predicate;
import org.rapidoid.test.TestCommons;
import org.testng.annotations.Test;

public class BasicDbTest extends TestCommons {

	@Test
	public void testCRUD() {

		long id1 = DB.insert(new Person("abc", 10));
		long id2 = DB.insert(new Person("f", 20));
		long id3 = DB.insert(new Person("xy", 30));

		Person p1 = DB.get(id1);
		Person p2 = DB.get(id2, Person.class);
		Person p3 = DB.get(id3);

		eq(p1.id, id1);
		eq(p1.name, "abc");
		eq(p1.age, 10);

		eq(p2.id, id2);
		eq(p2.name, "f");
		eq(p2.age, 20);

		eq(p3.id, id3);
		eq(p3.name, "xy");
		eq(p3.age, 30);

		Predicate<Person> pr = new Predicate<Person>() {
			@Override
			public boolean eval(Person p) throws Exception {
				return p.age > 18;
			}
		};

		List<Person> adults = DB.find(pr);

		eq(adults.size(), 2);
		eq(adults.get(0).id, id2);
		eq(adults.get(1).id, id3);
	}

}