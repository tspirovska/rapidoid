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

import java.util.Comparator;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.beany.Beany;
import org.rapidoid.db.DB;
import org.rapidoid.db.model.DbItems;
import org.rapidoid.lambda.Predicate;
import org.rapidoid.model.Item;
import org.rapidoid.model.Items;
import org.rapidoid.model.Models;
import org.rapidoid.util.Cls;
import org.rapidoid.widget.BootstrapWidgets;
import org.rapidoid.widget.DataManager;
import org.rapidoid.widget.FormWidget;
import org.rapidoid.widget.GridWidget;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class AppGUI extends BootstrapWidgets {

	public static <T> Items all(Class<T> type) {
		return new DbItems<T>(type, null, Beany.<T> comparator("id"));
	}

	public static <T> Items all(Class<T> type, String orderBy) {
		return new DbItems<T>(type, null, Beany.<T> comparator(orderBy));
	}

	public static <T> Items all(Class<T> type, Predicate<T> match, String orderBy) {
		return new DbItems<T>(type, match, Beany.<T> comparator(orderBy));
	}

	public static <T> Items all(Class<T> type, Predicate<T> match, Comparator<T> orderBy) {
		return new DbItems<T>(type, match, orderBy);
	}

	public static Item item(Object value) {
		return Models.item(value);
	}

	public static <T> Items beanItems(Class<T> beanType, T... beans) {
		return Models.beanItems(beanType, beans);
	}

	public static <T> GridWidget grid(Class<T> type, String sortOrder, int pageSize, String... properties) {
		return grid(all(type, sortOrder), sortOrder, pageSize, properties);
	}

	public static <T> GridWidget grid(Class<T> type, Predicate<T> match, String sortOrder, int pageSize,
			String... properties) {
		return grid(all(type, match, sortOrder), sortOrder, pageSize, properties);
	}

	public static <T> GridWidget grid(Class<T> type, Predicate<T> match, Comparator<T> orderBy, int pageSize,
			String... properties) {
		return grid(all(type, match, orderBy), null, pageSize, properties);
	}

	public static FormWidget show(Object bean, String... properties) {
		return show(Models.item(bean), properties);
	}

	public static FormWidget show(final Item item, String... properties) {
		return BootstrapWidgets.show(dataManager(), item, properties);
	}

	public static FormWidget edit(Object bean, String... properties) {
		return edit(Models.item(bean), properties);
	}

	public static FormWidget edit(final Item item, String... properties) {
		return BootstrapWidgets.edit(dataManager(), item, properties);
	}

	public static FormWidget create(Object bean, String... properties) {
		return create(Models.item(bean), properties);
	}

	public static FormWidget create(final Item item, String... properties) {
		return BootstrapWidgets.create(dataManager(), item, properties);
	}

	public static DataManager dataManager() {
		return Cls.customizable(DbDataManager.class, DB.db());
	}

}
