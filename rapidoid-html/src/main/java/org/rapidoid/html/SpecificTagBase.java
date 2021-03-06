package org.rapidoid.html;

/*
 * #%L
 * rapidoid-html
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
import org.rapidoid.var.Var;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public interface SpecificTagBase<TAG> {

	TAG content(Object... content);

	TAG append(Object... content);

	TAG prepend(Object... content);

	TAG copy();

	TAG withChild(int index, Object child);

	TAG attr(String attr, String value);

	TAG is(String attr, boolean value);

	<T> TAG bind(Var<T> var);

	TAG cmd(String cmd, Object... args);

	TAG navigate(String cmd, Object... args);

}
