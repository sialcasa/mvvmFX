/*
 * Copyright 2013 Alexander Casall - Saxonia Systems AG
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package de.saxsys.jfx.mvvm.viewloader;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.jfx.mvvm.base.View;

/**
 * Loader class for loading FXML and code behind from Fs.
 * 
 * @author alexander.casall
 */
public class ViewLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(ViewLoader.class);

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

		final FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		Parent view = null;
		try {
			view = (Parent) fxmlLoader.load(location.openStream());
		} catch (final IOException ex) {
			LOG.error("Error loading FXML :", ex);
		}
		final ViewTuple controllerTuple = new ViewTuple(
				(View<?>) fxmlLoader.getController(), view);
		return controllerTuple;
	}
}
