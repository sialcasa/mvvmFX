/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
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
package de.saxsys.jfx.mvvm.cdi.internal;

import javafx.util.Callback;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * This class is used as custom dependency injector for
 * {@link de.saxsys.jfx.mvvm.api.MvvmFX#setCustomDependencyInjector(javafx.util.Callback)}.
 * <p/>
 * This way the mvvmFX framework can use CDI for it's dependency injection mechanism.
 *
 * @author manuel.mauky
 */
class CdiInjector implements Callback<Class<?>, Object> {
	
	@Inject
	private Instance<Object> instances;
	
	@Override
	public Object call(Class<?> type) {
		return instances.select(type).get();
	}
}
