package de.saxsys.mvvmfx.examples.helloworld;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {

	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Hello World Application");

		final ViewTuple<HelloView, HelloViewModel> viewTuple = FluentViewLoader
				.fxmlView(HelloView.class)
				.load();

		final Parent root = viewTuple.getView();
		stage.setScene(new Scene(root));
		stage.show();
	}

}

