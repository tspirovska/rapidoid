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

import org.mockito.Mockito;
import org.rapidoid.Connection;
import org.rapidoid.buffer.Buf;
import org.rapidoid.buffer.BufGroup;
import org.testng.annotations.Test;

public class HttpParserTest extends HttpTestCommons {

	private static final String CRLF = "\r\n";

	private static String REQ1 = req("GET /foo/bar?a=5&b&n=4 HTTP/1.1|Host:www.test.com|Set-Cookie: aaa=2||", CRLF);
	private static String REQ2 = req(
			"POST /something/else/here?x=abc HTTP/STRANGE|Host:a.b.c.org|My-Header: same|My-Header: again|" + body("a"),
			CRLF);
	private static String REQ3 = req("PUT /books HTTP/1.0|  AAAAA: c = 2 |" + body("ab"), CRLF);
	private static String REQ4 = req("DELETE /?a&bb=c&d MY-PROTOCOL|" + body("abc"), CRLF);
	private static String REQ5 = req("GET / HTTP/1.1|" + body("abcd"), CRLF);
	private static String REQ6 = req("GET /?x A||", CRLF);

	private static final String CONTENT_LENGTH = "CoNtEnT-LenGth";

	private static String req(String s, String nl) {
		return s.replaceAll("\\|", nl);
	}

	private static String body(String s) {
		String body = "BODY" + s;
		return CONTENT_LENGTH + ": " + body.getBytes().length + "||" + body;
	}

	@Test
	public void shouldParseRequest1() {
		WebExchangeImpl req = parse(REQ1);

		eq(REQ1, req.verb, "GET");
		eq(REQ1, req.path, "/foo/bar");
		eqs(REQ1, req.params(), "a", "5", "b", "", "n", "4");
		eq(REQ1, req.protocol, "HTTP/1.1");
		eqs(REQ1, req.headers, "Host", "www.test.com", "Set-Cookie", "aaa=2");

		isNone(req.body);
	}

	@Test
	public void shouldParseRequest2() {
		WebExchangeImpl req = parse(REQ2);

		eq(REQ2, req.verb, "POST");
		eq(REQ2, req.path, "/something/else/here");
		eqs(REQ2, req.params(), "x", "abc");
		eq(REQ2, req.protocol, "HTTP/STRANGE");
		eqs(REQ2, req.headers, "Host", "a.b.c.org", "My-Header", "same", "My-Header", "again", CONTENT_LENGTH, "5");
		eq(REQ2, req.body, "BODYa");
	}

	@Test
	public void shouldParseRequest3() {
		WebExchangeImpl req = parse(REQ3);

		eq(REQ3, req.verb, "PUT");
		eq(REQ3, req.path, "/books");
		eqs(REQ3, req.params());
		eq(REQ3, req.protocol, "HTTP/1.0");
		eqs(REQ3, req.headers, "AAAAA", "c = 2", CONTENT_LENGTH, "6");
		eq(REQ3, req.body, "BODYab");
	}

	@Test
	public void shouldParseRequest4() {
		WebExchangeImpl req = parse(REQ4);

		eq(REQ4, req.verb, "DELETE");
		eq(REQ4, req.path, "/");
		eqs(REQ4, req.params(), "a", "", "bb", "c", "d", "");
		eq(REQ4, req.protocol, "MY-PROTOCOL");
		eqs(REQ4, req.headers, CONTENT_LENGTH, "7");
		eq(REQ4, req.body, "BODYabc");
	}

	@Test
	public void shouldParseRequest5() {
		WebExchangeImpl req = parse(REQ5);

		eq(REQ5, req.verb, "GET");
		eq(REQ5, req.path, "/");
		eqs(REQ5, req.params());
		eq(REQ5, req.protocol, "HTTP/1.1");
		eqs(REQ5, req.headers, CONTENT_LENGTH, "8");
		eq(REQ5, req.body, "BODYabcd");
	}

	@Test
	public void shouldParseRequest6() {
		WebExchangeImpl req = parse(REQ6);

		eq(REQ6, req.verb, "GET");
		eq(REQ6, req.path, "/");
		eqs(REQ6, req.params(), "x", "");
		eq(REQ6, req.protocol, "A");
		eqs(REQ6, req.headers);
		isNone(req.body);
	}

	private WebExchangeImpl parse(String reqs) {
		WebExchangeImpl req = new WebExchangeImpl();

		Buf reqbuf = new BufGroup(10).from(reqs, "test");

		Connection conn = Mockito.mock(Connection.class);
		Mockito.when(conn.input()).thenReturn(reqbuf);

		req.setConnection(conn);

		HttpParser parser = new HttpParser();
		parser.parse(reqbuf, req);

		return req;
	}

}