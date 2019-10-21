package de.saxsys.mvvmfx.utils.validation.cssvisualizer;

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CssVisualizerExampleApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent parent = FluentViewLoader.fxmlView(CssVisualizerView.class).load().getView();

		primaryStage.setScene(new Scene(parent));
		primaryStage.show();
	}
}
