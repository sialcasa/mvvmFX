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
