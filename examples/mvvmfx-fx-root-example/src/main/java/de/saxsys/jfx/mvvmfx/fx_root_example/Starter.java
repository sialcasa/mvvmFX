package de.saxsys.jfx.mvvmfx.fx_root_example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Starter extends Application {
	
	
	public static void main(String... args) {
		Application.launch(args);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("fx:root example application");
		
		VBox root = new VBox();
		
		LabeledTextField textField = new LabeledTextField();
		
		root.getChildren().add(textField);
		
		stage.setScene(new Scene(root, 300, 50));
		stage.show();
	}
}
