package org.rapidoid.pages;

/*
 * #%L
 * rapidoid-pages
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

import org.rapidoid.model.Model;
import org.rapidoid.pages.bootstrap.TableWidget;
import org.rapidoid.pages.entity.Person;
import org.rapidoid.pages.html.ButtonTag;
import org.rapidoid.pages.html.DivTag;
import org.rapidoid.pages.html.InputTag;
import org.rapidoid.pages.html.SpanTag;
import org.rapidoid.util.U;

public class PlaygroundWidget extends Widget {

	public DivTag grid(int page) {
		Object[] data = { new Person("nick", 22), new Person("doe", 44) };

		if (data.length > 0) {
			return div(new TableWidget(Model.beanItems(data)));
		} else {
			return div(_("No results!"));
		}
	}

	public SpanTag counter(int start) {

		final Var<Integer> num = var(start);

		ButtonTag b1 = button("+").onClick(new Handler<ButtonTag>() {
			@Override
			public void handle(ButtonTag target) {
				num.set(num.get() + 1);
			}
		});

		ButtonTag b2 = button("-").onClick(new Handler<ButtonTag>() {
			@Override
			public void handle(ButtonTag target) {
				num.set(num.get() + 1);
			}
		});

		return span(b2, span(num), b1);
	}

	public DivTag adder() {

		final InputTag input = input().css("border: 1px;");
		final DivTag coll = div();

		ButtonTag b2 = button("+").onClick(new Handler<ButtonTag>() {
			@Override
			public void handle(ButtonTag target) {
				U.debug("click", "button", target);
				coll.append(p("added ", input.value()));
			}
		});

		Var<Integer> counter = var(1);

		b2.onClick(Do.inc(counter, 2), Do.dec(counter, 1));

		return div(span(input, b2), coll);
	}

	@Override
	protected Object contents() {
		return html(grid(1), counter(10), adder());
	}

}