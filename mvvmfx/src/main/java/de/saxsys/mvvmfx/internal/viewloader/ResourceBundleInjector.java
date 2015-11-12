/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.internal.viewloader;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.InjectResourceBundle;

/**
 * This helper class is used to encapsulate the logic for injection of {@link ResourceBundle} into fields annotated with
 * {@link InjectResourceBundle} in Views/ViewModels.
 * 
 */
class ResourceBundleInjector {
	
	/**
	 * Try to inject the given {@link ResourceBundle} into the given target instance.
	 *
	 * @param target
	 *            the View/ViewModel instance that the ResourceBundle will be injected to. May not be <code>null</code>.
	 * @param resourceBundle
	 *            the ResourceBundle instance that is used. Can be <code>null</code> if no ResourceBundle was provided
	 *            by the user.
	 */
	static void injectResourceBundle(Object target, ResourceBundle resourceBundle) {
		final List<Field> fieldsWithAnnotation = ReflectionUtils
				.getFieldsWithAnnotation(target, InjectResourceBundle.class);
		
		final boolean notAssignableFieldPresent = fieldsWithAnnotation.stream()
				.filter(field -> !field.getType().isAssignableFrom(ResourceBundle.class))
				.findAny().isPresent();
		
		if (notAssignableFieldPresent) {
			throw new IllegalStateException(
					"The class ["
							+ target
							+ "] has at least one field with the annotation @InjectResourceBundle but the field is not of type ResourceBundle.");
		}
		
		
		if (resourceBundle == null) {
			
			if (!fieldsWithAnnotation.isEmpty()) {
				
				final boolean nonOptionalFieldsPresent = fieldsWithAnnotation.stream()
						.flatMap(field -> Arrays.stream(field.getAnnotationsByType(InjectResourceBundle.class)))
						.filter(annotation -> !annotation.optional())
						.findAny().isPresent();
				
				// if all annotated fields are marked as "optional", no exception has to be thrown.
				if (nonOptionalFieldsPresent) {
					throw new IllegalStateException(
							"The class ["
									+ target
									+ "] expects a ResourceBundle to be injected but no ResourceBundle was defined while loading.");
				}
			}
		} else {
			fieldsWithAnnotation
					.forEach(field -> {
						if (field.getType().isAssignableFrom(ResourceBundle.class)) {
							ReflectionUtils.setField(field, target, resourceBundle);
						} else {
							throw new IllegalStateException(
									"The class ["
											+ target
											+ "] has a field with the @InjectResourceBundle annotation but the type of the field doesn't match ResourceBundle");
						}
					});
		}
	}
	
}
