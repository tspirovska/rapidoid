package org.rapidoid.worker;

/*
 * #%L
 * rapidoid-utils
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

import java.util.Queue;

import org.rapidoid.util.U;

public class WorkerQueue<T> {

	final Queue<T> queue;

	final int limit;

	public WorkerQueue(Queue<T> queue, int limit) {
		this.queue = queue;
		this.limit = limit;
	}

	public boolean canGet() {
		return queue.peek() != null;
	}

	public boolean canPut() {
		return limit <= 0 || queue.size() < limit;
	}

	public T take() {
		T item;

		while ((item = queue.poll()) == null) {
			U.sleep(100);
		}

		return item;
	}

	public void put(T item) {
		while (!queue.offer(item)) {
			U.sleep(100);
		}
	}

}