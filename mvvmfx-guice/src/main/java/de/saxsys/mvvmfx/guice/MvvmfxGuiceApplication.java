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
package de.saxsys.mvvmfx.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.guice.internal.MvvmfxModule;
import de.saxsys.mvvmfx.internal.MvvmfxApplication;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has to be extended by the user to build a javafx application powered by Guice.
 *
 * @author manuel.mauky
 */
public abstract class MvvmfxGuiceApplication extends Application implements MvvmfxApplication {
	
	private Stage primaryStage;


	/**
	 * This method is called by the javafx runtime when the application is initialized.
	 * See {@link Application#init()} for more details.
	 * <p>
	 * In this method the initialization of the guice container is done.
	 * For this reason, this method is marked as final and cannot be overwritten by users.
	 * <p>
	 * Please use {@link #initMvvmfx()} for your own initialization logic.
	 *
	 * @throws Exception
	 */
	public final void init() throws Exception {
		List<Module> modules = new ArrayList<>();

		modules.add(new MvvmfxModule());
		
		modules.add(new AbstractModule() {
			@Override
			protected void configure() {
				bind(HostServices.class).toProvider(MvvmfxGuiceApplication.this::getHostServices);
				
				bind(Stage.class).toProvider(() -> primaryStage);
				
				bind(Parameters.class).toProvider(MvvmfxGuiceApplication.this::getParameters);
			}
		});
		
		
		this.initGuiceModules(modules);

		final Injector injector = Guice.createInjector(modules);
		MvvmFX.setCustomDependencyInjector(injector::getInstance);

		injector.injectMembers(this);

		this.initMvvmfx();
	}
	
	/**
	 * This method is overridden to initialize the mvvmFX framework. Override the
	 * {@link #startMvvmfx(javafx.stage.Stage)} method for your application entry point and startup code instead of this
	 * method.
	 */
	public final void start(Stage stage) throws Exception {
		this.primaryStage = stage;

		this.startMvvmfx(stage);
	}
	
	@Override
	public final void stop() throws Exception {
		stopMvvmfx();
	}
	
	/**
	 * Configure your guice modules. Use the given list and
	 * add your modules to it.
	 *
	 * @param modules
	 *            module list
	 * @throws Exception
	 *             exc
	 */
	public void initGuiceModules(List<Module> modules) throws Exception {
	}
}
