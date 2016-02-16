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

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author manuel.mauky
 */
public class MainView extends VBox implements JavaView<MainViewModel> {
	
	private HBox container = new HBox();
	private Button rollAllDicesButton = new Button("Roll all Dices");
	private ProgressBar progressBar = new ProgressBar();
	
	@InjectViewModel
	private MainViewModel viewModel;
	
	public MainView(){
		this.getChildren().add(container);
		
		HBox footer = new HBox();
		footer.setSpacing(5);
		footer.getChildren().addAll(rollAllDicesButton, progressBar);
		progressBar.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(progressBar, Priority.ALWAYS);
		
		this.getChildren().add(footer);
		this.setPadding(new Insets(5));
		this.setSpacing(5);
	}
	
	public void initialize() {
		final ViewTuple<SubView, SubViewModel> subViewTupleOne = FluentViewLoader.javaView(SubView.class).load();
		viewModel.setChildOne(subViewTupleOne.getViewModel());
		container.getChildren().add(subViewTupleOne.getView());
		
		final ViewTuple<SubView, SubViewModel> subViewTupleTwo = FluentViewLoader.javaView(SubView.class).load();
		viewModel.setChildTwo(subViewTupleTwo.getViewModel());
		container.getChildren().add(subViewTupleTwo.getView());
		
		final ViewTuple<SubView, SubViewModel> subViewTupleThree = FluentViewLoader.javaView(SubView.class).load();
		viewModel.setChildThree(subViewTupleThree.getViewModel());
		container.getChildren().add(subViewTupleThree.getView());
		
		viewModel.init();


		final Command command = viewModel.getRollAllDicesCommand();
		
		rollAllDicesButton.setOnAction(event -> command.execute());
		rollAllDicesButton.disableProperty().bind(command.notExecutableProperty());
		progressBar.progressProperty().bind(command.progressProperty());
		
	}
	
}
