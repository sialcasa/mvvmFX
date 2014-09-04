package de.saxsys.mvvmfx.contacts.ui.contactform;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ContactFormView implements FxmlView<ContactFormViewModel> {

	@FXML
	public TextField firstnameInput;
	@FXML
	public TextField titleInput;
	@FXML
	public TextField lastnameInput;
	@FXML
	public TextField roleInput;
	@FXML
	public TextField departmentInput;
	@FXML
	public TextField mobileNumberInput;
	@FXML
	public TextField emailInput;
	@FXML
	public TextField phoneNumberInput;
	@FXML
	public DatePicker birthdayInput;
	
	
	@InjectViewModel
	private ContactFormViewModel viewModel;
	
	
	public void initialize(){
		firstnameInput.textProperty().bindBidirectional(viewModel.firstnameProperty());
		lastnameInput.textProperty().bindBidirectional(viewModel.lastnameProperty());
		titleInput.textProperty().bindBidirectional(viewModel.titleProperty());
		roleInput.textProperty().bindBidirectional(viewModel.roleProperty());
		departmentInput.textProperty().bindBidirectional(viewModel.departmentProperty());
		mobileNumberInput.textProperty().bindBidirectional(viewModel.mobileNumberProperty());
		phoneNumberInput.textProperty().bindBidirectional(viewModel.phoneNumberProperty());
		emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
		birthdayInput.valueProperty().bindBidirectional(viewModel.birthdayProperty());

		// init the validation. The logic for the actual validation is hidden in the viewModel.
		viewModel.initValidationForFirstname(firstnameInput);
		viewModel.initValidationForLastname(lastnameInput);
		viewModel.initValidationForBirthday(birthdayInput);

		viewModel.initValidationForEmail(emailInput);
		viewModel.initValidationForPhoneNumber(phoneNumberInput);
		viewModel.initValidationForMobileNumber(mobileNumberInput);
	}
	
	public ContactFormViewModel getViewModel(){
		return viewModel;
	}
	
}
