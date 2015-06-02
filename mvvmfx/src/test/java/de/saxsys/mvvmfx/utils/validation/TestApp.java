package de.saxsys.mvvmfx.utils.validation;/**
 * @author manuel.mauky
 */

import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualization;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualization;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestApp extends Application {

	private TextField input;
	private Validator validator;
	private ValidationVisualization validationVisualization = new ControlsFxVisualization();


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		input = new TextField();

		root.getChildren().add(input);
		
		Button ok = new Button("OK");
		root.getChildren().add(ok);
		
		root.setSpacing(10);
		root.setPadding(new Insets(10));
		
		initValidation();
		
		primaryStage.setScene(new Scene(root));
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	private void initValidation() {
		
		
		validator = new Validator();
		validator.addRule(Rules.notEmpty(input.textProperty()), ValidationMessage.error("may not be empty"));
//		validator.addRule(input.textProperty().length().lessThan(5),
//				ValidationMessage.error("Length must be greater than 5"));
		
		
		validationVisualization.visualize(validator.getValidationResult(), input);

		
	}
}
