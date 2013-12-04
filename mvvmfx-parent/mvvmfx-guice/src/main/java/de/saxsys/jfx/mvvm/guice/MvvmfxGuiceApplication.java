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
package de.saxsys.jfx.mvvm.guice;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Module;
import de.saxsys.jfx.mvvm.api.MvvmFX;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.util.List;

/**
 * This class has to be extended by the user to build a javafx application powered by Guice.
 *
 * @author manuel.mauky
 */
public abstract class MvvmfxGuiceApplication extends GuiceApplication {


    @Inject
    private GuiceInjector guiceInjector;

    @Override
    public final void init(List<Module> modules) throws Exception {
        modules.add(new MvvmfxModule());

        this.initGuiceModules(modules);
    }

    /**
     * This method is overridden to initialize the mvvmFX framework. Override the {@link
     * #startMvvmfx(javafx.stage.Stage) method for your application entry point and startup code instead of this
     * method.
     */
    public final void start(Stage stage) throws Exception {
        MvvmFX.setCustomDependencyInjector(guiceInjector);

        this.startMvvmfx(stage);
    }

    /**
     * Override this method with your application startup logic.
     * <p/>
     * This method is a wrapper method for javafx's {@link javafx.application.Application#start(javafx.stage.Stage)}.
     */
    public abstract void startMvvmfx(Stage stage) throws Exception;

    /**
     * Configure the guice modules.
     *
     * @param modules module list
     * @throws Exception exc
     */
    public abstract void initGuiceModules(List<Module> modules) throws Exception;
}
