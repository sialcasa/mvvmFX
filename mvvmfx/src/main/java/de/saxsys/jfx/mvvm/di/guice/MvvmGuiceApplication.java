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
package de.saxsys.jfx.mvvm.di.guice;

import static de.saxsys.jfx.mvvm.di.guice.ModuleFactory.createModule;

import java.util.List;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Module;

import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;
import de.saxsys.jfx.mvvm.di.InjectionWrapper;
import de.saxsys.jfx.mvvm.notifications.DefaultNotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;


/**
 * MVVMApplication which has to be extended to enable the guice support.
 * 
 * @author sialcasa
 */
public abstract class MvvmGuiceApplication extends GuiceApplication {
	@Override
	public void init(List<Module> modules) throws Exception {
		modules.add(createModule(FXMLLoaderWrapper.class, GuiceFXMLLoaderWrapper.class));
		modules.add(createModule(InjectionWrapper.class, GuiceInjectionWrapper.class));
		modules.add(createModule(NotificationCenter.class, DefaultNotificationCenter.class));
		
		initGuiceModules(modules);
	}

	/**
	 * Configure the guice modules.
	 * 
	 * @param modules
	 *            module list
	 * @throws Exception
	 *             exc
	 */
	public abstract void initGuiceModules(List<Module> modules) throws Exception;

}
