/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.validation;

/**
 * @author manuel.mauky
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualizer;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizer;

public class TestApp extends Application {
	
	private TextField input;
	private ObservableRuleBasedValidator validator;
	private ValidationVisualizer validationVisualizer = new ControlsFxVisualizer();
	
	
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
		
		
		validator = new ObservableRuleBasedValidator();
		validator.addRule(ObservableRules.notEmpty(input.textProperty()), ValidationMessage.error("may not be empty"));
		// validator.addRule(input.textProperty().length().lessThan(5),
		// ValidationMessage.error("Length must be greater than 5"));
		
		
		validationVisualizer.initVisualization(validator.getValidationStatus(), input, true);
		
		
	}
}
