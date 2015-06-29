package de.saxsys.mvvmfx.utils.validation.crossfieldexample;

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author manuel.mauky
 */
public class CrossFieldExampleApp extends Application {
	
	public static void main(String... args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		final Parent root = FluentViewLoader.fxmlView(RegisterFormView.class).load().getView();
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
	}
}
