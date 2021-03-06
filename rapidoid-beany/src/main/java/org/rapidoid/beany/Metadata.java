package org.rapidoid.beany;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.util.Cls;
import org.rapidoid.util.U;

/*
 * #%L
 * rapidoid-beany
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

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public class Metadata {

	public static Map<Class<?>, Annotation> classAnnotations(Class<?> clazz) {
		clazz = Cls.unproxy(clazz);

		Map<Class<?>, Annotation> annotations = U.map();

		for (Annotation ann : clazz.getAnnotations()) {
			annotations.put(ann.annotationType(), ann);
		}

		return annotations;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T classAnnotation(Class<?> clazz, Class<T> annotationClass) {
		clazz = Cls.unproxy(clazz);
		return (T) classAnnotations(clazz).get(annotationClass);
	}

	public static Map<Class<?>, Annotation> propAnnotations(Class<?> clazz, String property) {
		clazz = Cls.unproxy(clazz);

		Map<Class<?>, Annotation> annotations = U.map();
		Prop prop = Beany.property(clazz, property, false);

		if (prop != null) {
			for (Annotation ann : prop.getDeclaringType().getAnnotations()) {
				annotations.put(ann.annotationType(), ann);
			}
			for (Annotation ann : prop.getAnnotations()) {
				annotations.put(ann.annotationType(), ann);
			}
		}

		return annotations;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T propAnnotation(Class<?> clazz, String property, Class<T> annotationClass) {
		clazz = Cls.unproxy(clazz);
		return (T) propAnnotations(clazz, property).get(annotationClass);
	}

	public static boolean isAnnotated(Class<?> clazz, Class<?> annotation) {
		clazz = Cls.unproxy(clazz);
		return classAnnotations(clazz).containsKey(annotation);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T get(Annotation[] annotations, Class<T> annotationClass) {
		if (annotations != null) {
			for (Annotation ann : annotations) {
				if (annotationClass.isAssignableFrom(ann.annotationType())) {
					return (T) ann;
				}
			}
		}
		return null;
	}

}
