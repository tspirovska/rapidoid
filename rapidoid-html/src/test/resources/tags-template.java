package org.rapidoid.pages.html;

/*
 * #%L
 * rapidoid-html
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

import java.util.Map;

import org.rapidoid.pages.DynamicContent;
import org.rapidoid.pages.Tag;
import org.rapidoid.pages.Var;
import org.rapidoid.pages.impl.DynamicContentWrapper;
import org.rapidoid.pages.impl.FileTemplate;
import org.rapidoid.pages.impl.GuiContext;
import org.rapidoid.pages.impl.MultiLanguageText;
import org.rapidoid.pages.impl.TagInterceptor;
import org.rapidoid.pages.impl.VarImpl;

public class Tags {

	protected final GuiContext ctx = new GuiContext();

	public Object _(String multiLanguageText, Object... formatArgs) {
		return new MultiLanguageText(multiLanguageText, formatArgs);
	}

	public <T> Var<T> var(T value) {
		return new VarImpl<T>(ctx, value);
	}

	public Object render(String templateFileName, Object... namesAndValues) {
		return new FileTemplate(templateFileName, namesAndValues);
	}

	public Object dynamic(DynamicContent dynamic) {
		return new DynamicContentWrapper(dynamic);
	}

	public Tag<?> get(String hnd) {
		return ctx.get(hnd);
	}

	public Map<String, Tag<?>> changedTags() {
		return ctx.changedTags();
	}

	public Map<String, String> changedContent() {
		return ctx.changedContent();
	}

	protected UlTag ul_li(Object... listItems) {
		UlTag list = ul();

		for (Object item : listItems) {
			list.append(li(item));
		}

		return list;
	}

	public <TAG extends Tag<?>> TAG tag(Class<TAG> clazz, String tagName, Object... contents) {
		return TagInterceptor.create(ctx, clazz, tagName, contents);
	}

{{tags}}
}