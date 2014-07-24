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

import javafx.application.Application;
import javafx.stage.Stage;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import de.saxsys.jfx.mvvm.api.MvvmFX;

/**
 * The class is instantiated by the javafx framework. The purpose of this class is to startup the weld container and
 * setup the {@link de.saxsys.jfx.mvvm.cdi.internal.CdiInjector} for the mvvmFX framework.
 * <p/>
 * Additionally it fires the stage that was provided by javafx as an cdi event. This event is observed by
 * {@link de.saxsys.jfx.mvvm.cdi.MvvmfxCdiApplication} to startup the users application.
 *
 * @author manuel.mauky
 */
public class WeldStartupHelper extends Application {
	
	public class StartupEvent {
		private Parameters parameters;
		
		private Stage stage;
		
		public StartupEvent(Stage stage, Parameters parameters) {
			this.stage = stage;
			this.parameters = parameters;
		}
		
		public Stage getStage() {
			return stage;
		}
		
		public Parameters getParameters() {
			return parameters;
		}
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		WeldContainer weldContainer = new Weld().initialize();
		CdiInjector cdiInjector = weldContainer.instance().select(CdiInjector.class).get();
		
		MvvmFX.setCustomDependencyInjector(cdiInjector);
		
		weldContainer.event().fire(new StartupEvent(stage, this.getParameters()));
	}
}
