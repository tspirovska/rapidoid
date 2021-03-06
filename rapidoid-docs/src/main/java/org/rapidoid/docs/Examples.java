package org.rapidoid.docs;

/*
 * #%L
 * rapidoid-docs
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

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.app.AppHandler;
import org.rapidoid.config.Conf;
import org.rapidoid.db.DB;
import org.rapidoid.http.HTTP;
import org.rapidoid.http.HTTPServer;
import org.rapidoid.http.HttpBuiltins;
import org.rapidoid.lambda.Mapper;
import org.rapidoid.oauth.OAuth;
import org.rapidoid.util.AppCtx;
import org.rapidoid.util.Classes;
import org.rapidoid.util.IO;
import org.rapidoid.util.Scan;
import org.rapidoid.util.U;
import org.rapidoid.util.UTILS;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class Examples {

	private static final String JAVA_KEYWORDS = "abstract|continue|for|new|switch|assert|default|goto|package|synchronized|boolean|do|if|private|this|break|double|implements|protected|throw|byte|else|import|public|throws|case|enum|instanceof|return|transient|catch|extends|int|short|try|char|final|interface|static|void|class|finally|long|strictfp|volatile|const|float|native|super|while";

	public static void main(String[] args) {
		Conf.args("oauth-no-state");
		DB.destroy();

		String path = "../../rapidoid.github.io/";
		U.must(new File(path).exists());

		int exampleN = 1;
		while (true) {
			List<Class<?>> classes = Scan.pkg("org.rapidoid.docs.eg" + exampleN);
			if (classes.isEmpty()) {
				break;
			}

			generate(path, exampleN, classes);

			exampleN++;
		}

		generateIndex(path, exampleN - 1);
		DB.shutdown();
		DB.destroy();
	}

	private static void generateIndex(String path, int examplesN) {
		String docsT = IO.load("docs-template.html");
		String egT = IO.load("example-template.html");

		String examples = "";

		String tab = "\\t";
		String str1 = "(\"[^\"]*?\")";
		String str2 = "('[^']*?')";
		String kw = "\\b(" + JAVA_KEYWORDS + ")\\b";
		String cls = "\\b([A-Z]\\w+?)\\b";

		String rr = "(?:" + U.join("|", str1, str2, tab, kw, cls) + ")";

		Pattern p = Pattern.compile("\n");

		String[] titles = IO.load("examples.txt").split("\\n");
		U.must(titles.length >= examplesN - 1);

		for (int i = 2; i <= examplesN; i++) {

			String snippFile = "src/main/java/org/rapidoid/docs/eg" + i + "/App.java";
			String snippet = IO.load(snippFile);
			U.must(snippet != null, "Cannot find: " + snippFile);

			snippet = cleanSnippet(snippet);

			Matcher m = p.matcher(snippet);
			U.must(m.find());
			int pos = m.start();

			String desc = snippet.substring(0, pos).trim();
			U.must(desc.startsWith("//"));
			desc = desc.substring(2).trim();

			snippet = snippet.substring(pos).trim();

			snippet = col(rr, snippet);

			String example = UTILS.fillIn(egT, "n", i + "");
			example = UTILS.fillIn(example, "code", snippet);
			example = UTILS.fillIn(example, "title", titles[i - 2]);
			example = UTILS.fillIn(example, "desc", desc);
			examples += example;
		}

		String html = UTILS.fillIn(docsT, "examples", examples);

		IO.save(path + "index.html", html);
	}

	private static String cleanSnippet(String s) {
		String comm = "#L%\n */";
		int p = s.indexOf(comm);
		U.must(p > 0);
		s = s.substring(p + comm.length()).trim();
		return s;
	}

	private static String col(String rr, String snippet) {
		snippet = UTILS.replace(snippet, rr, new Mapper<String[], String>() {
			@Override
			public String map(String[] src) throws Exception {
				String s = src[0];
				char ch = s.charAt(0);
				if (Character.isUpperCase(ch)) {
					return "<span class=\"_code_cls\">" + s + "</span>";
				} else if (ch == '"' || ch == "'".charAt(0)) {
					return "<span class=\"_code_str\">" + s + "</span>";
				} else if (s.equals("\t")) {
					return "    ";
				} else {
					return "<span class=\"_code_kw\">" + s + "</span>";
				}
			}
		});
		return snippet;
	}

	public static void generate(String path, int exampleN, List<Class<?>> classes) {

		AppCtx.setClasses(Classes.from(classes));

		HTTPServer server = HTTP.server().build();
		OAuth.register(server);
		HttpBuiltins.register(server);
		server.serve(new AppHandler());
		server.start();

		path += "examples/";
		new File(path).mkdir();

		path += exampleN + "/";
		new File(path).mkdir();

		saveTo(server, "/", path + "index.html");
		saveTo(server, "/search", path + "search.html");
		saveTo(server, "/rapidoid.js", path + "../rapidoid.js");
		saveTo(server, "/rapidoid.css", path + "../rapidoid.css");

		for (int i = 1; i <= 5; i++) {
			saveTo(server, "//bootstrap/css/theme-" + i + ".css", path + "../theme-" + i + ".css");
		}

		for (Class<?> cls : classes) {
			String name = cls.getSimpleName();
			if (name.endsWith("Screen")) {
				name = U.mid(name, 0, -6);
				String page = U.uncapitalized(name);
				String url = "/" + page;
				saveTo(server, url, path + page + ".html");
			}
		}

		server.shutdown();
	}

	private static void saveTo(HTTPServer server, String url, String filename) {
		String out = server.process(U.format("GET %s HTTP/1.1\r\nHost: a.b\r\n\r\n", url));

		int p = out.indexOf("\r\n\r\n");
		out = out.substring(p + 4);

		// TODO remove these hacks
		out = out.replace("/rapidoid.css", "../rapidoid.css");
		out = out.replace("/rapidoid.js", "../rapidoid.js");
		out = out.replace("/bootstrap/css/theme-", "../theme-");
		out = out.replace("\"//", "\"http://");
		out = out.replace("href=\"/\"", "href=\"index.html\"");
		out = out.replaceAll("(href|action)=\\\"/(\\w+)\\\"", "$1=\"$2.html\"");

		IO.save(filename, out);
	}

}
