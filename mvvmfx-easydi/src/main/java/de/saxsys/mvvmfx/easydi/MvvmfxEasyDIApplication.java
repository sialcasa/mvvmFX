/*******************************************************************************
 * Copyright 2016 Sven Lechner
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.easydi;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.internal.MvvmfxApplication;
import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

import javax.inject.Provider;

/**
 * This class has to be extended by the user to build a javaFX application
 * powered by EasyDI as dependency injection library.
 *
 * <p>
 *
 * Please note: EasyDI only supports constructor injection.
 * However when JavaFX bootstraps the {@link Application} constructors with arguments are not supported
 * for the application starter class.
 * This means that you can't inject dependencies into your application starter class directly.
 * Instead you can use the {@link #initEasyDi(EasyDI)} method for this purpose.
 * See the following code example:
 *
 * <pre>
 * public class MyApp extends MvvmfxEasyDIApplication {
 *
 *     private Service service;
 *
 *    {@literal @}Override
 *     public void initEasyDi(EasyDI context) {
 *         this.service = context.getInstance(Service.class);
 *     }
 *
 *
 *    {@literal @}Override
 *     public void startMvvmfx(Stage stage) {
 *         service.someAction(); // use the injected service
 *
 *         // load your UI
 *     }
 * }
 *
 * </pre>
 *
 * <p>
 * <b>Lifecycle notice.</b>
 * The application class goes through this lifecycle:
 * <ol>
 *     <li>{@link #initEasyDi(EasyDI)}</li>
 *     <li>{@link #initMvvmfx()}</li>
 *     <li>{@link #startMvvmfx(Stage)}</li>
 *     <li>{@link #stopMvvmfx()}</li>
 * </ol>
 *
 * This means that dependencies that were obtained in the {@link #initEasyDi(EasyDI)} method can be used
 * in the {@link #initMvvmfx()} and {@link #startMvvmfx(Stage)} methods.
 *
 */
public abstract class MvvmfxEasyDIApplication extends Application implements MvvmfxApplication {

	private EasyDI easyDI;

	private Stage primaryStage;

	@Override
	public final void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		// bind EasyDI to MvvmFX framework as a custom di
		MvvmFX.setCustomDependencyInjector(easyDI::getInstance);
		startMvvmfx(primaryStage);
	}

	@Override
	public final void stop() throws Exception {
		super.stop();
		stopMvvmfx();
	}

	@Override
	public final void init() throws Exception {
		super.init();

		// create the di context and bind all mvvmFX classes
		easyDI = new EasyDI();
		easyDI.bindProvider(HostServices.class, () -> getHostServices());
		easyDI.bindProvider(Parameters.class, () -> getParameters());
		easyDI.bindProvider(Stage.class, () -> primaryStage);
		// make sure all custom bindings are created before any UI code gets started
		initEasyDi(easyDI);

		// let the user init stuff
		initMvvmfx();
	}

	/**
	 * This method is used for initialization of the EasyDI context.
	 * A typical usage is to configure custom interface bindings like
	 * {@link EasyDI#bindInterface(Class, Class)} or {@link EasyDI#bindProvider(Class, Provider)}.
	 * <p>
	 *
	 * Additionally this method can be used to get instances of dependencies that are needed
	 * for the start process of the application (for example in the {@link #startMvvmfx(Stage)} method).
	 * <p>
	 * <b>Lifecycle notice:</b>
	 * This method is invoked by the framework <b>before</b> the invocation of {@link MvvmfxEasyDIApplication#initMvvmfx()}
	 * and {@link MvvmfxEasyDIApplication#startMvvmfx(Stage)}.
	 *
	 *
	 * @param context
	 *         The {@code EasyDI} instance that is responsible for injecting all needed instances in the current application.<br/>
	 *
	 * @throws Exception
	 *         If any error occurs while binding interfaces.
	 */
	protected void initEasyDi(EasyDI context) throws Exception {
	}
}
