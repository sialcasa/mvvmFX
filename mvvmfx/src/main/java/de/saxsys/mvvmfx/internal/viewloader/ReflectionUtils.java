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

import de.saxsys.mvvmfx.internal.SideEffectWithException;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * This class is used to encapsulate operations for the handling of reflection.
 * 
 * @author manuel.mauky
 */
public class ReflectionUtils {

	/**
	 * Returns all fields with the given annotation. Only fields that are declared in the actual class of the instance
	 * are considered (i.e. no fields from super classes). This includes private fields.
	 *
	 * @param target
	 *            the instance that's class is used to find annotations.
	 * @param annotationType
	 *            the type of the annotation that is searched for.
	 * @return a List of Fields that are annotated with the given annotation.
	 */
	public static List<Field> getFieldsWithAnnotation(Object target, Class<? extends Annotation> annotationType) {
		return ReflectionUtils.getFieldsFromClassHierarchy(target.getClass())
                .stream()
				.filter(field -> field.isAnnotationPresent(annotationType))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Returns all fields of the given type and all parent types (except Object).
	 * <br>
	 * The difference to {@link Class#getFields()} is that getFields only returns public fields while this method will
	 * return all fields whatever the access modifier is.
	 * <br>
     *
	 * The difference to {@link Class#getDeclaredFields()} is that getDeclaredFields returns all fields (with all
	 * modifiers) from the given class but not from super classes. This method instead will return all fields of all
	 * modifiers from all super classes up in the class hierarchy, except from Object itself.
	 *
	 * @param type the type whose fields will be searched.
	 * @return a list of field instances.
	 */
	public static List<Field> getFieldsFromClassHierarchy(Class<?> type) {
		
		final List<Field> classFields = new ArrayList<>();
		classFields.addAll(Arrays.asList(type.getDeclaredFields()));
		final Class<?> parentClass = type.getSuperclass();
		
		if (parentClass != null && !(parentClass.equals(Object.class))) {
			List<Field> parentClassFields = getFieldsFromClassHierarchy(parentClass);
			classFields.addAll(parentClassFields);
		}
		
		return classFields;
	}
	
	
	
	/**
	 * Helper method to execute a callback on a given member. This method encapsulates the error handling logic and the
	 * handling of accessibility of the member.
	 *
	 * After the callback is executed the accessibility of the member will be reset to the originally state.
	 *
	 * @param member
	 *            the member that is made accessible to run the callback
	 * @param callable
	 *            the callback that will be executed.
	 * @param errorMessage
	 *            the error message that is used in the exception when something went wrong.
	 *			
	 * @return the return value of the given callback.
	 *		
	 * @throws IllegalStateException
	 *             when something went wrong.
	 */
	public static <T> T accessMember(final AccessibleObject member, final Callable<T> callable, String errorMessage) {
		if (callable == null) {
			return null;
		}
		return AccessController.doPrivileged((PrivilegedAction<T>) () -> {
			boolean wasAccessible = member.isAccessible();
			try {
				member.setAccessible(true);
				return callable.call();
			} catch (Exception exception) {
				throw new IllegalStateException(errorMessage, exception);
			} finally {
				member.setAccessible(wasAccessible);
			}
		});
	}
	
	
	/**
	 * This method can be used to set (private/public) fields to a given value by reflection. Handling of accessibility
	 * and errors is encapsulated.
	 *
	 * @param field
	 *            the field that's value should be set.
	 * @param target
	 *            the instance of which the field will be set.
	 * @param value
	 *            the new value that the field should be set to.
	 */
	public static void setField(final Field field, Object target, Object value) {
		accessMember(field, () -> field.set(target, value),
				"Cannot set the field [" + field.getName() + "] of instance [" + target + "] to value [" + value + "]");
	}
	
	
	/**
	 * Helper method to execute a callback on a given member. This method encapsulates the error handling logic and the
	 * handling of accessibility of the member. The difference to
	 * {@link ReflectionUtils#accessMember(AccessibleObject, Callable, String)} is that this method takes a callback that doesn't
	 * return anything but only creates a sideeffect.
	 *
	 * After the callback is executed the accessibility of the member will be reset to the originally state.
	 *
	 * @param member
	 *            the member that is made accessible to run the callback
	 * @param sideEffect
	 *            the callback that will be executed.
	 * @param errorMessage
	 *            the error message that is used in the exception when something went wrong.
	 *			
	 * @throws IllegalStateException
	 *             when something went wrong.
	 */
	public static void accessMember(final AccessibleObject member, final SideEffectWithException sideEffect, String errorMessage) {
		if (sideEffect == null) {
			return;
		}
		AccessController.doPrivileged((PrivilegedAction<?>) () -> {
			boolean wasAccessible = member.isAccessible();
			try {
				member.setAccessible(true);
				sideEffect.call();
			} catch (Exception exception) {
				throw new IllegalStateException(errorMessage, exception);
			} finally {
				member.setAccessible(wasAccessible);
			}
			return null;
		});
	}
}