package de.saxsys.jfx.mvvm.viewloader;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.jfx.mvvm.base.MVVMView;
import de.saxsys.jfx.mvvm.base.MVVMViewModel;

/**
 * Loader class for loading FXML and code behind from Fs.
 * 
 * @author alexander.casall
 */
public class MVVMViewLoader {

	private static final Logger LOG = LoggerFactory
			.getLogger(MVVMViewLoader.class);

	/**
	 * Gets a {@link MVVMView} and a {@link MVVMViewModel} for a given
	 * {@link ViewController}.
	 * 
	 * @param view
	 *            to lookup
	 * @return tuple
	 */
	public MVVMTuple getTuple(final MVVMViewNames view) {
		final MVVMTuple controllerTuple = loadController(view.getResource());
		return controllerTuple;
	}

	/**
	 * Helper for loading a Controller from filesystem.
	 * 
	 * @param resource
	 *            to load the controller from
	 * @return
	 */
	private MVVMTuple loadController(final String resource) {
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
		final MVVMTuple controllerTuple = new MVVMTuple(
				(MVVMView<?>) fxmlLoader.getController(), view);
		return controllerTuple;
	}
}
