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
package de.saxsys.mvvmfx.utils.validation.crossfieldexample;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

/**
 * @author manuel.mauky
 */
public class RegisterFormView implements FxmlView<RegisterFormViewModel> {
	
	@FXML
	public PasswordField pwInput;
	@FXML
	public PasswordField pwRepeatInput;
	@FXML
	public Label message;
	@FXML
	public Button okButton;
	
	@InjectViewModel
	private RegisterFormViewModel viewModel;
	
	
	public void initialize() {
		
		viewModel.passwordProperty().bindBidirectional(pwInput.textProperty());
		viewModel.passwordRepeatProperty().bindBidirectional(pwRepeatInput.textProperty());
		
		updateMessage();
		
		viewModel.getValidation().getMessages().addListener((ListChangeListener<ValidationMessage>) c -> {
			updateMessage();
		});
		okButton.disableProperty().bind(viewModel.getValidation().validProperty().not());
		
	}
	
	private void updateMessage() {
		message.setText(
				viewModel.getValidation().getHighestMessage().map(ValidationMessage::getMessage)
						.orElse("everythink ok"));
	}
	
	public void ok() {
		System.out.println("Register!");
	}
}
