package org.rapidoid.demo.db;

/*
 * #%L
 * rapidoid-demo
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

import java.io.ByteArrayOutputStream;

import org.rapidoid.db.DB;
import org.rapidoid.demo.pojo.Person;
import org.rapidoid.util.U;

public class DbSerializationBenchmark {

	public static void main(String[] args) {

		U.args(args);

		int size = U.option("size", 1000000);
		int loops = U.option("loops", 100);

		for (int i = 0; i < size; i++) {
			DB.insert(new Person("john doe" + i, i));
		}

		System.out.println("measuring...");

		U.benchmark("save " + size + " records", loops, new Runnable() {
			@Override
			public void run() {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				DB.save(out);
				byte[] bytes = out.toByteArray();
			}
		});
	}

}