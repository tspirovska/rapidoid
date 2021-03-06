package org.rapidoid.widget;

/*
 * #%L
 * rapidoid-pages
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
import org.rapidoid.html.tag.TdTag;
import org.rapidoid.model.Items;

// TODO use grid decorator instead
@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class HighlightedGridWidget extends GridWidget {

	private String regex;

	public HighlightedGridWidget(Items items, String sortOrder, int pageSize, String... properties) {
		super(items, sortOrder, pageSize, properties);
	}

	public GridWidget regex(String regex) {
		this.regex = regex;
		return this;
	}

	@Override
	protected TdTag cell(Object value) {
		String s = String.valueOf(value);
		return super.cell(highlight(s, regex));
	}

}
