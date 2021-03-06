package org.rapidoid.app;

/*
 * #%L
 * rapidoid-app
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

import java.util.Map;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.beany.Beany;
import org.rapidoid.config.Conf;
import org.rapidoid.db.DB;
import org.rapidoid.http.HTTP;
import org.rapidoid.http.HTTPServer;
import org.rapidoid.http.HttpBuiltins;
import org.rapidoid.http.HttpExchange;
import org.rapidoid.log.Log;
import org.rapidoid.oauth.OAuth;
import org.rapidoid.util.Cls;
import org.rapidoid.util.Scan;
import org.rapidoid.util.U;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class Apps {

	private static final String BUILT_IN_SCREEN_SUFFIX = "BuiltIn";

	public static void main(String[] args) {
		run(args);
	}

	public static void run(String... args) {
		Conf.args(args);
		Log.args(args);

		Log.info("Loaded database", "size", DB.size());

		HTTPServer server = HTTP.server().build();

		OAuth.register(server);
		HttpBuiltins.register(server);

		server.serve(new AppHandler());

		server.start();
	}

	public static String screenName(Class<?> screenClass) {
		String name = screenClass.getSimpleName();
		if (name.endsWith(BUILT_IN_SCREEN_SUFFIX)) {
			name = U.mid(name, 0, -BUILT_IN_SCREEN_SUFFIX.length());
		}
		return U.mid(name, 0, -6);
	}

	public static String screenUrl(Class<?> screenClass) {
		String url = "/" + screenName(screenClass).toLowerCase();
		return url.equals("/home") ? "/" : url;
	}

	public static AppClasses scanAppClasses(HttpExchange x) {
		return scanAppClasses(x, null);
	}

	public static synchronized AppClasses scanAppClasses(HttpExchange x, ClassLoader classLoader) {

		Map<String, Class<?>> services = Cls.classMap(Scan.bySuffix("Service", null, classLoader));
		Map<String, Class<?>> pages = Cls.classMap(Scan.bySuffix("Page", null, classLoader));
		Map<String, Class<?>> apps = Cls.classMap(Scan.byName("App", null, classLoader));
		Map<String, Class<?>> screens = Cls.classMap(Scan.bySuffix("Screen", null, classLoader));

		final Class<?> appClass = !apps.isEmpty() ? apps.get("App") : TheDefaultApp.class;

		AppClasses APP_CLASSES = new AppClasses(appClass, services, pages, screens);
		return APP_CLASSES;
	}

	@SuppressWarnings("unchecked")
	public static <T> T config(Object obj, String configName, T byDefault) {
		Object val = Beany.getPropValue(obj, configName, null);
		return val != null ? (T) val : byDefault;
	}

	public static boolean addon(Object obj, String configName) {
		return config(obj, configName, false) || config(obj, "full", true);
	}

}
