package de.saxsys.mvvmfx.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
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
	 * A functional interface that is used in this class to express callbacks that don't take any argument and don't
	 * return anything. Such a callback have to work only by side effects.
	 */
	@FunctionalInterface
	public static interface SideEffect {
		void call() throws Exception;
	}
	
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
		return Arrays.stream(target.getClass().getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(annotationType))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Helper method to execute a callback on a given field. This method encapsulates the error handling logic and the
	 * handling of accessibility of the field.
	 *
	 * After the callback is executed the accessibility of the field will be reset to the originally state.
	 *
	 * @param field
	 *            the field that is made accessible to run the callback
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
	public static <T> T accessField(final Field field, final Callable<T> callable, String errorMessage) {
		if (callable == null) {
			return null;
		}
		return AccessController.doPrivileged((PrivilegedAction<T>) () -> {
			boolean wasAccessible = field.isAccessible();
			try {
				field.setAccessible(true);
				return callable.call();
			} catch (Exception exception) {
				throw new IllegalStateException(errorMessage, exception);
			} finally {
				field.setAccessible(wasAccessible);
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
		accessField(field, () -> field.set(target, value),
				"Cannot set the field [" + field.getName() + "] of instance [" + target + "] to value [" + value + "]");
	}
	
	
	/**
	 * Helper method to execute a callback on a given field. This method encapsulates the error handling logic and the
	 * handling of accessibility of the field. The difference to
	 * {@link ReflectionUtils#accessField(Field, Callable, String)} is that this method takes a callback that doesn't
	 * return anything but only creates a sideeffect.
	 *
	 * After the callback is executed the accessibility of the field will be reset to the originally state.
	 *
	 * @param field
	 *            the field that is made accessible to run the callback
	 * @param sideEffect
	 *            the callback that will be executed.
	 * @param errorMessage
	 *            the error message that is used in the exception when something went wrong.
	 *
	 * @throws IllegalStateException
	 *             when something went wrong.
	 */
	public static void accessField(final Field field, final SideEffect sideEffect, String errorMessage) {
		if (sideEffect == null) {
			return;
		}
		AccessController.doPrivileged((PrivilegedAction) () -> {
			boolean wasAccessible = field.isAccessible();
			try {
				field.setAccessible(true);
				sideEffect.call();
			} catch (Exception exception) {
				throw new IllegalStateException(errorMessage, exception);
			} finally {
				field.setAccessible(wasAccessible);
			}
			return null;
		});
	}


	public static Object invokePrivateMethod(final Method method, Object target, String errorMessage, Object...params){
		return AccessController.doPrivileged((PrivilegedAction) () -> {
			boolean wasAccessible = method.isAccessible();
			try{
				method.setAccessible(true);
				return method.invoke(target, params);
			} catch (Exception e) {
				throw new IllegalStateException(errorMessage, e);
			} finally {
				method.setAccessible(wasAccessible);
			}
		});
	}

}