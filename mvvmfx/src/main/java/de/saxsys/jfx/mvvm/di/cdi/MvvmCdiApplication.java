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
package de.saxsys.jfx.mvvm.di.cdi;


import javax.enterprise.event.Observes;

import javafx.application.Application;
import javafx.stage.Stage;

import org.jboss.weld.environment.se.Weld;


/**
 * Base Application class that needs to be extended to use CDI as Dependency
 * Injection framework.
 * 
 * When the JavaFX {@link Stage} is ready an {@link StartupEvent} is fired that will contain the
 * {@link Stage}. You should implement an CDI observer (with {@link Observes}) that listens for this event
 * to get the stage and build your UI.
 * 
 * @author manuel.mauky
 * 
 */
public class MvvmCdiApplication extends Application {
	
	private Weld weld;
	
	@Override
	public final void init() throws Exception {
		super.init();
		weld = new Weld();
	}
	
	
	@Override
	public final void start(Stage primaryStage) throws Exception {
		weld.initialize().event().fire(new StartupEvent(primaryStage,getParameters()));
	}
	
	@Override
	public final void stop() throws Exception {
		weld.shutdown();
		super.stop();
	}
	
	
	
}
