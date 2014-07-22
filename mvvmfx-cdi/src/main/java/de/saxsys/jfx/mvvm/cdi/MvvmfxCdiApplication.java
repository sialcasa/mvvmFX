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
package de.saxsys.jfx.mvvm.cdi;

import de.saxsys.jfx.mvvm.cdi.internal.WeldStartupHelper;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;


/**
 * This class has to be extended by the user to build a javafx application powered by CDI.
 *
 * @author manuel.mauky
 */
public abstract class MvvmfxCdiApplication {


    /**
     * The event that was fired to start this application. It contains values that are provided by the javafx
     * Application
     */
    private WeldStartupHelper.StartupEvent startupEvent;

    /**
     * This method is equivalent to javafx's {@link javafx.application.Application#launch(String...)}. You have to call
     * this method to startup you application.
     * <p/>
     * Please notice that there is not launch method that takes a parameter of type {@link Class} as first param as it
     * is available in {@link javafx.application.Application}. The reason is that we need this method internally to
     * support the startup with CDI.
     *
     * @param args the arguments from the console interface.
     */
    public static void launch(String... args) {
        Application.launch(WeldStartupHelper.class, args);
    }

    /**
     * This is an observer method for the {@link javafx.stage.Stage}. This event is fired by {@link
     * de.saxsys.jfx.mvvm.cdi.internal.WeldStartupHelper}. This way we can get the stage from javafx into our CDI
     * environment.
     */
    private void listenToStartup(@Observes WeldStartupHelper.StartupEvent startupEvent) throws Exception {
        this.startupEvent = startupEvent;
        this.start(startupEvent.getStage());
    }

    /**
     * Override this method to setup your application. This method is equivalent to javafx's {@link
     * javafx.application.Application#start(javafx.stage.Stage)} method.
     */
    public abstract void start(Stage stage) throws Exception;


    /**
     * Returns the application parameters. See {@link javafx.application.Application#getParameters()}.
     */
    public Application.Parameters getParameters() {
        if (startupEvent != null) {
            return startupEvent.getParameters();
        }
        return null;
    }

}
