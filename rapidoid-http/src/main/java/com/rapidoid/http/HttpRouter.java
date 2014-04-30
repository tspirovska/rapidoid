package com.rapidoid.http;

/*
 * #%L
 * rapidoid-http
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

import org.rapidoid.buffer.Buf;
import org.rapidoid.data.Range;
import org.rapidoid.util.SimpleHashTable;
import org.rapidoid.util.SimpleList;
import org.rapidoid.util.U;

public class HttpRouter implements Router {

	private class Route {
		Handler handler;
		byte[] action;
		byte[] path;

		@Override
		public String toString() {
			return new String(action) + ":" + new String(path);
		}
	}

	private final SimpleHashTable<Route> routes = new SimpleHashTable<Route>(10000);

	private Handler genericHandler;

	@Override
	public void generic(Handler handler) {
		this.genericHandler = handler;
	}

	@Override
	public void route(String action, String url, Handler handler) {
		// verbs are case-sensitive, forcing uppercase convention
		if (!action.matches("[A-Z_][A-Z0-9_]*")) {
			throw new IllegalArgumentException(
					"Only uppercase letters, digits and underscore are allowed! Invalid action: " + action);
		}

		if (!url.matches("[a-zA-Z0-9_/]*")) {
			throw new IllegalArgumentException("Invalid url: " + url);
		}

		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}

		if (!url.startsWith("/")) {
			url = "/" + url;
		}

		U.info("Registering handler for: %s %s", action, url);

		addRoute(action, url, handler);
	}

	private void addRoute(String action, String path, Handler handler) {
		assert action.length() >= 1;
		assert path.length() >= 1;

		Route route = new Route();
		route.handler = handler;
		route.action = action.getBytes();
		route.path = path.getBytes();

		long hash = hash(action, path);

		routes.put(hash, route);
	}

	private long hash(String action, String path) {
		int hash = action.charAt(0) * 17 + action.length() * 19 + path.charAt(0);
		return hash;
	}

	private long hash(Buf buf, Range action, Range path) {
		int hash = buf.get(action.start) * 17 + action.length * 19 + buf.get(path.start);
		return hash;
	}

	@Override
	public boolean dispatch(WebExchangeImpl x) {

		Buf buf = x.input();
		Range action = x.verb().range();
		Range path = x.path().range();

		long hash = hash(buf, action, path);
		SimpleList<Route> candidates = routes.get(hash);

		if (candidates != null) {
			for (int i = 0; i < candidates.size(); i++) {
				Route route = candidates.get(i);

				if (buf.matches(action, route.action, true) && buf.startsWith(path, route.path, true)) {
					int pos = path.start + route.path.length;
					if (path.limit() == pos || buf.get(pos) == '/') {
						x.setSubpath(pos, path.limit());
						route.handler.handle(x);
						return true;
					}
				}
			}
		}

		if (genericHandler != null) {
			x.setSubpath(path.start, path.limit());
			genericHandler.handle(x);
			return true;
		}

		return false;
	}

}