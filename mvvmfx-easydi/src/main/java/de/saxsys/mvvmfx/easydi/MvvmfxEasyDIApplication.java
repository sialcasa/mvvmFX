/*******************************************************************************
 * Copyright 2016 Sven Lechner
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
package de.saxsys.mvvmfx.easydi;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.internal.MvvmfxApplication;
import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

/**
 * Created by Sven on 22/06/16.
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
        configureBindings(easyDI);

        // let the user init stuff
        initMvvmfx();
    }

    /**
     * This method is used to configure custom interface bindings.
     *
     * @param easyDI
     *         The {@code EasyDI} instance that is responsible for injecting all needed instances in the current application.<br/>
     *
     * @throws Exception
     *         If any error occurs while binding interfaces.
     */
    protected void configureBindings(EasyDI easyDI) throws Exception {}
}
