package de.saxsys.mvvmfx.utils.dialogs;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.function.Supplier;

/**
 * Created by Sven on 21/06/16.
 */
public class DialogHelper {

	// prevent instantiation from outside
	private DialogHelper() {}

	public static Stage initDialog(Window owner, Supplier<Parent> content, BooleanProperty visibleProperty, String...
			                                                                                                        css) {
		// stage style could be an additional argument
		Stage stage = new Stage();
		stage.initOwner(owner);
		// same goes for modality
		stage.initModality(Modality.APPLICATION_MODAL);

		configureScene(stage, content, css);

		if (visibleProperty != null) {
			// TODO: cant the value be bound by using #bind?
			// if the stage closes, set the property to false
			stage.setOnCloseRequest(event -> visibleProperty.set(false));
			// if the value changes, fire an event
			visibleProperty.addListener((observable, oldValue, newValue) -> {
				if(newValue) {
					stage.show();
				} else {
					stage.close();
				}
			});
		}


		return stage;
	}

	// don't really know why this if statement exists. I kept it because there might be a bug that I am not
	// aware of. But creating a new Scene each time the method is called shouldn't be a problem.
	private static void configureScene(Stage stage, Supplier<Parent> content, String... css) {
		// no idea why this if statement is here. Doesn't make much sense to me. Just create a new Scene every time
		// and we are fine
		if (stage.getScene() == null) {
			// create new scene and apply css
			Scene scene = new Scene(content.get());
			scene.getStylesheets().addAll(css);
			stage.setScene(scene);

			// can be customized later on to
			stage.sizeToScene();
		}
	}
}
