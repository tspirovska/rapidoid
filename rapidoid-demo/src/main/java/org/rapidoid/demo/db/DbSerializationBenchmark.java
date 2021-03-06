package org.rapidoid.demo.db;

/*
 * #%L
 * rapidoid-demo
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

import java.io.ByteArrayOutputStream;
import java.util.Collections;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.config.Conf;
import org.rapidoid.db.impl.inmem.DbEntityConstructor;
import org.rapidoid.db.impl.inmem.JacksonEntitySerializer;
import org.rapidoid.inmem.InMem;
import org.rapidoid.util.UTILS;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class DbSerializationBenchmark {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Conf.args(args);

		final InMem db = new InMem(null, new JacksonEntitySerializer(null), new DbEntityConstructor(null),
				Collections.EMPTY_SET, null);

		int size = Conf.option("size", 100000);
		int loops = Conf.option("loops", 100);

		for (int i = 0; i < size; i++) {
			db.insert(new Person("john doe" + i, i));
		}

		System.out.println("measuring...");

		UTILS.benchmark("save " + size + " records", loops, new Runnable() {
			@Override
			public void run() {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				db.saveTo(out);
				out.toByteArray();
			}
		});
	}

}
