package org.rapidoid.demo.pages;

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

import org.rapidoid.demo.pojo.Person;
import org.rapidoid.html.Tag;
import org.rapidoid.html.TagEventHandler;
import org.rapidoid.html.tag.ATag;
import org.rapidoid.model.Item;
import org.rapidoid.model.Model;
import org.rapidoid.pages.bootstrap.FormWidget;
import org.rapidoid.pages.bootstrap.NavbarBootstrapPage;

@SuppressWarnings("serial")
public class BeanFormPage extends NavbarBootstrapPage {

	private ATag brand;

	private FormWidget form;

	public BeanFormPage() {
		brand = a("Edit person").href("/beanForm.html");

		Tag<?>[] buttons = { btn("Save", new TagEventHandler<Tag<?>>() {
			@Override
			public void handle(Tag<?> target) {
				target.append("+");
			}
		}), btn("Cancel") };

		Person person = new Person("abc", 22);
		Item item = Model.item(person);

		form = new FormWidget(item, buttons);

		setContent(page());
	}

	@Override
	protected Object pageContent() {
		return row(col4(form));
	}

	@Override
	protected Tag<?> brand() {
		return brand;
	}

	@Override
	protected Object[] navbarContent() {
		return arr();
	}

}