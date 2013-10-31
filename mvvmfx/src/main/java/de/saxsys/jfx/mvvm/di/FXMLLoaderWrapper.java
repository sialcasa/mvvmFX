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
package de.saxsys.jfx.mvvm.di;

import java.io.IOException;
import java.net.URL;

import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

import javafx.fxml.FXMLLoader;

/**
 * Wrapper class to encapsulate the {@link FXMLLoader}. This is used because
 * the FXMLLoader depends on the Dependency-Injection framework. 
 * With this wrapper we can use the functionality to load FXML files in the application 
 * without a direct dependency to the DI framework.
 * 
 * @author manuel.mauky
 *
 */
public interface FXMLLoaderWrapper {
	
	/**
	 * Loads the fxml document for the given {@link URL}. There is no exception handling provided by this 
	 * method.
	 */
	ViewTuple load(URL location) throws IOException;
}
