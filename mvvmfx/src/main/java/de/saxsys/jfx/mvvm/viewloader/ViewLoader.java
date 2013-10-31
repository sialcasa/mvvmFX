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

package de.saxsys.jfx.mvvm.viewloader;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;

/**
 * Loader class for loading FXML and code behind from Fs. There are following
 * options for loading the FXML:
 * 
 * <ul>
 * <li>Providing the code behind class (controller) by calling
 * {@link #loadViewTuple(Class)}</li>
 * <li>Providing a path to the FXML file by calling
 * {@link #loadViewTuple(String)}</li>
 * </ul>
 * 
 * @author alexander.casall
 */
public final class ViewLoader {

	private static final Logger LOG = LoggerFactory.getLogger(ViewLoader.class);

	@Inject
	private FXMLLoaderWrapper fxmlLoaderWrapper;

	/**
	 * Load the view (Code behind + Node from FXML) by a given Code behind
	 * class. Care - The fxml has to be in the same package like the clazz.
	 * 
	 * @param clazz
	 *            which is the code behind of a fxml
	 * @return the tuple
	 */
	public <ViewType extends ViewModel> ViewTuple<ViewType> loadViewTuple(
			Class<? extends View> ViewType) {
		String pathToFXML = "/"
				+ ViewType.getPackage().getName().replaceAll("\\.", "/") + "/"
				+ ViewType.getSimpleName() + ".fxml";

		return loadViewTuple(pathToFXML);
	}

	/**
	 * Load the view (Code behind + Node from FXML) by a given resource path.
	 * 
	 * @param resource
	 *            to load the controller from
	 * @return tuple which is <code>null</code> if an error occures.
	 */
	public ViewTuple loadViewTuple(final String resource) {
		// Load FXML file
		final URL location = getClass().getResource(resource);
		if (location == null) {
			LOG.error("Error loading FXML - can't load from given resourcepath: "
					+ resource);
			return null;
		}
		
		try {
			
			final ViewTuple controllerTuple = fxmlLoaderWrapper.load(location);
			return controllerTuple;
		} catch (final IOException ex) {
			LOG.error("Error loading FXML :", ex);
			return null;
		}

	}
}
