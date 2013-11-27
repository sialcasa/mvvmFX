/*******************************************************************************
 * Copyright 2013 Alexander Casall
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
package de.saxsys.jfx.mvvm.base.view;

import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.di.DependencyInjector;
import javafx.fxml.Initializable;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for a MVVMView - you have to say which View Model it uses.
 * Then you can use the embedded {@link ViewModel} property which is typed
 * correctly.
 * 
 * @author alexander.casall
 * 
 * @param <ViewModelType>
 *            type
 */
public abstract class View<ViewModelType extends ViewModel> implements
		Initializable {

	/**
	 * Creates a View. If no View model has been set and the child class not a
	 * {@link ViewWithoutViewModel}, an exception is going to be thrown.
	 */
	public View() {
		if (returnedClass() == null && !(this instanceof ViewWithoutViewModel)) {
			throw new IllegalStateException(
					"The View has no defined View Model. If you want to achive this use the class ViewWithoutViewModel.java instead of View.java for the inheritance");
		}
	}

	// View Model
	private ViewModelType viewModel;

	private DependencyInjector injectionFacade = DependencyInjector.getInstance();

	/**
	 * @return the View Model which represents the data that should be displayed
	 *         by the view
	 */
	public final ViewModelType getViewModel() {
		if (viewModel == null && !(viewModel instanceof ViewWithoutViewModel)) {
			viewModel = injectionFacade.getInstanceOf(returnedClass());
		}
		return viewModel;
	}

	// http://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime
	/**
	 * Method returns class.
	 * 
	 * @return Class<T extends ViewModelType>
	 */
	@SuppressWarnings("unchecked")
	private Class<ViewModelType> returnedClass() {
		return (Class<ViewModelType>) getTypeArguments(View.class, getClass())
				.get(0);
	}

	/**
	 * Get the underlying class for a type, or null if the type is a variable
	 * type.
	 * 
	 * @param type
	 *            the type
	 * @return the underlying class
	 */
	@SuppressWarnings("rawtypes")
	private Class<?> getClass(Type type) {
		if (type instanceof Class) {
			return (Class) type;
		} else if (type instanceof ParameterizedType) {
			return getClass(((ParameterizedType) type).getRawType());
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type)
					.getGenericComponentType();
			Class<?> componentClass = getClass(componentType);
			if (componentClass != null) {
				return Array.newInstance(componentClass, 0).getClass();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Get the actual type arguments a child class has used to extend a generic
	 * base class.
	 * 
	 * @param baseClass
	 *            the base class
	 * @param childClass
	 *            the child class
	 * @return a list of the raw classes for the actual type arguments.
	 */
	@SuppressWarnings("rawtypes")
	public <T> List<Class<?>> getTypeArguments(Class<T> baseClass,
			Class<? extends T> childClass) {
		Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
		Type type = childClass;
		// start walking up the inheritance hierarchy until we hit baseClass
		while (!getClass(type).equals(baseClass)) {
			if (type instanceof Class) {
				// there is no useful information for us in raw types, so just
				// keep going.
				type = ((Class) type).getGenericSuperclass();
			} else {
				ParameterizedType parameterizedType = (ParameterizedType) type;

				Class<?> rawType = (Class) parameterizedType.getRawType();

				Type[] actualTypeArguments = parameterizedType
						.getActualTypeArguments();
				TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
				for (int i = 0; i < actualTypeArguments.length; i++) {
					resolvedTypes
							.put(typeParameters[i], actualTypeArguments[i]);
				}

				if (!rawType.equals(baseClass)) {
					type = rawType.getGenericSuperclass();
				}
			}
		}

		// finally, for each actual type argument provided to baseClass,
		// determine (if possible)
		// the raw class for that type argument.
		Type[] actualTypeArguments;
		if (type instanceof Class) {
			actualTypeArguments = ((Class) type).getTypeParameters();
		} else {
			actualTypeArguments = ((ParameterizedType) type)
					.getActualTypeArguments();
		}
		List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
		// resolve types by chasing down type variables.
		for (Type baseType : actualTypeArguments) {
			while (resolvedTypes.containsKey(baseType)) {
				baseType = resolvedTypes.get(baseType);
			}
			typeArgumentsAsClasses.add(getClass(baseType));
		}
		return typeArgumentsAsClasses;
	}
}
