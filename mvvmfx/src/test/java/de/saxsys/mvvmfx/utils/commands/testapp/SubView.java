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
package de.saxsys.mvvmfx.utils.commands.testapp;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


/**
 * @author manuel.mauky
 */
public class SubView extends VBox implements JavaView<SubViewModel> {
	
	private ListView<Integer> numbers = new ListView<>();
	private Button rollDiceButton = new Button("Roll the Dice");
	private CheckBox activeCheckBox = new CheckBox();
	
	private ProgressBar progressBar = new ProgressBar();
	
	@InjectViewModel
	private SubViewModel viewModel;
	
	public SubView(){
		this.getChildren().add(numbers);
		HBox footer = new HBox();
		footer.getChildren().add(activeCheckBox);
		footer.getChildren().add(rollDiceButton);
		footer.getChildren().add(progressBar);
		footer.setSpacing(5);
		progressBar.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(progressBar, Priority.ALWAYS);
		
		this.getChildren().add(footer);
		
		this.setPadding(new Insets(5));
		this.setSpacing(5);
	}
	
	public void initialize(){
		activeCheckBox.selectedProperty().bindBidirectional(viewModel.activeProperty());
		
		numbers.setItems(viewModel.numbersProperty());
		
		final Command rollDiceCommand = viewModel.getRollDiceCommand();
		rollDiceButton.setOnAction(event -> {
			rollDiceCommand.execute();
		});
		rollDiceButton.disableProperty().bind(rollDiceCommand.notExecutableProperty());
		progressBar.progressProperty().bind(rollDiceCommand.progressProperty());
	}
	
	
}
