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

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * CDI event class that is used at applications startup. This event 
 * will be fired when the javafx application is ready. 
 * 
 * @author manuel.mauky
 *
 */
public class StartupEvent {
	
	private final Stage primaryStage;
	
	private final Application.Parameters parameters;

	public StartupEvent(Stage primaryStage, Application.Parameters parameters){
		this.primaryStage = primaryStage;
		this.parameters = parameters;
	}

	/**
	 * @return the primary javafx {@link Stage}.
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * @return the parameters from the command line.
	 */
	public Application.Parameters getParameters() {
		return parameters;
	}
	
}
