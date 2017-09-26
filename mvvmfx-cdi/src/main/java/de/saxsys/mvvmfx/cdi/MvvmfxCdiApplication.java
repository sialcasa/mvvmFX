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
package de.saxsys.mvvmfx.cdi;

import de.saxsys.mvvmfx.internal.MvvmfxApplication;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.cdi.internal.MvvmfxProducer;


/**
 * This class has to be extended by the user to build a javafx application powered by CDI.
 *
 * @author manuel.mauky
 */
public abstract class MvvmfxCdiApplication extends Application implements MvvmfxApplication {

	private final BeanManager beanManager;
	private CreationalContext<MvvmfxCdiApplication> ctx;
    private InjectionTarget<MvvmfxCdiApplication> injectionTarget;
    private final SeContainer container;

    @Inject
	private MvvmfxProducer producer;

    public MvvmfxCdiApplication() {
        container = SeContainerInitializer
                .newInstance()
                .initialize();

		MvvmFX.setCustomDependencyInjector((type) -> container.select(type).get());

		MvvmfxProducer mvvmfxProducer = container.select(MvvmfxProducer.class).get();
		mvvmfxProducer.setHostServices(getHostServices());

		beanManager = container.getBeanManager();
	}

	/**
	 * This method is overridden to initialize the mvvmFX framework. Override the
	 * {@link #startMvvmfx(javafx.stage.Stage)} method for your application entry point and startup code instead of this
	 * method.
	 */
	@Override
	public final void start(Stage primaryStage) throws Exception {
		producer.setPrimaryStage(primaryStage);

		startMvvmfx(primaryStage);
	}


	/**
	 * This method is called when the javafx application is initialized. See
	 * {@link javafx.application.Application#init()} for more details.
	 *
	 * Unlike the original init method in {@link javafx.application.Application} this method contains logic to
	 * initialize the CDI container. Therefor it's important to call <code>super.init()</code> when you override this
	 * method.
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final void init() throws Exception {
		ctx = beanManager.createCreationalContext(null);
		injectionTarget = beanManager.createInjectionTarget(
				beanManager.createAnnotatedType((Class<MvvmfxCdiApplication>) this.getClass()));

		injectionTarget.inject(this, ctx);
		injectionTarget.postConstruct(this);

		producer.setApplicationParameters(getParameters());

		initMvvmfx();
	}


	/**
	 * This method is called when the application should stop. See {@link javafx.application.Application#stop()} for
	 * more details.
	 *
	 * Unlike the original stop method in {@link javafx.application.Application} this method contains logic to release
	 * resources managed by the CDI container. Therefor it's important to call <code>super.stop()</code> when you
	 * override this method.
	 *
	 * @throws Exception
	 */
	@Override
	public final void stop() throws Exception {
		stopMvvmfx();

		injectionTarget.preDestroy(this);
		injectionTarget.dispose(this);

		ctx.release();

		container.close();
	}
}
