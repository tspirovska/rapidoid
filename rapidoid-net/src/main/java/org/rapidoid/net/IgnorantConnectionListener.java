package org.rapidoid.net;

/*
 * #%L
 * rapidoid-net
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

import org.rapidoid.Connection;

public class IgnorantConnectionListener implements ConnectionListener {

	@Override
	public void beforeWriting(Connection conn, Object tag, int kind) {
	}

	@Override
	public void afterWriting(Connection conn, Object tag, int kind) {
	}

	@Override
	public void onDone(Connection conn, Object tag) {
	}

	@Override
	public void onComplete(RapidoidConnection gettyConnection, Object tag) {
	}

}