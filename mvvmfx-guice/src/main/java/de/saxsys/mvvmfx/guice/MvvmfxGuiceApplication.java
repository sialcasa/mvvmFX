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

import java.util.List;

import de.saxsys.mvvmfx.internal.MvvmfxApplication;
import javafx.application.HostServices;
import javafx.stage.Stage;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.guice.internal.GuiceInjector;
import de.saxsys.mvvmfx.guice.internal.MvvmfxModule;

/**
 * This class has to be extended by the user to build a javafx application powered by Guice.
 *
 * @author manuel.mauky
 */
public abstract class MvvmfxGuiceApplication extends GuiceApplication implements MvvmfxApplication{
	
	
	@Inject
	private GuiceInjector guiceInjector;
	
	private Stage primaryStage;
	
	@Override
	public final void init(List<Module> modules) throws Exception {
		modules.add(new MvvmfxModule());
		
		modules.add(new AbstractModule() {
			@Override
			protected void configure() {
				bind(HostServices.class).toProvider(new Provider<HostServices>() {
					@Override
					public HostServices get() {
						return getHostServices();
					}
				});

				bind(Stage.class).toProvider(new Provider<Stage>() {
					@Override
					public Stage get() {
						return primaryStage;
					}
				});

				bind(Parameters.class).toProvider(new Provider<Parameters>() {
					@Override
					public Parameters get() {
						return getParameters();
					}
				});
			}
		});
		
		
		this.initGuiceModules(modules);
		this.initMvvmfx();
	}
	
	/**
	 * This method is overridden to initialize the mvvmFX framework. Override the
	 * {@link #startMvvmfx(javafx.stage.Stage)} method for your application entry point and startup code instead of this
	 * method.
	 */
	public final void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		MvvmFX.setCustomDependencyInjector(guiceInjector);
		
		this.startMvvmfx(stage);
	}

	@Override
	public final void stop() throws Exception {
		stopMvvmfx();
	}

	/**
	 * Configure the guice modules.
	 *
	 * @param modules
	 *            module list
	 * @throws Exception
	 *             exc
	 */
	public void initGuiceModules(List<Module> modules) throws Exception{
	}
}
