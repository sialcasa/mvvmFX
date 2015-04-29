package de.saxsys.mvvmfx.utils.commands.testapp;

/**
 * @author manuel.mauky
 */

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		final Parent view = FluentViewLoader.javaView(MainView.class).load().getView();
		
		primaryStage.setScene(new Scene(view));
		primaryStage.show();
	}
}
