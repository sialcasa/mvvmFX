package de.zerotask.voices.ui;

import javafx.stage.Stage;

/**
 * Marker interface for views that need a reference to their stage.
 * 
 * @author SirWindfield
 *
 */
public interface Displayable {

	/**
	 * Sets the stage that has been used to show the view content.
	 * 
	 * @param stage
	 *            The stage.
	 */
	void setStage(Stage stage);
}
