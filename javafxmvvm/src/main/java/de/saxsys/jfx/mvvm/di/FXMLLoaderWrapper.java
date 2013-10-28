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
