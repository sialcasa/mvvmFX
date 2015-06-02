package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualization;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualization;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
	
	private ValidationVisualization validationVisualization = new ControlsFxVisualization();
	
	@InjectViewModel
	private ContactFormViewModel viewModel;
	
	public void initialize() {
		firstnameInput.textProperty().bindBidirectional(viewModel.firstnameProperty());
		lastnameInput.textProperty().bindBidirectional(viewModel.lastnameProperty());
		titleInput.textProperty().bindBidirectional(viewModel.titleProperty());
		roleInput.textProperty().bindBidirectional(viewModel.roleProperty());
		departmentInput.textProperty().bindBidirectional(viewModel.departmentProperty());
		mobileNumberInput.textProperty().bindBidirectional(viewModel.mobileNumberProperty());
		phoneNumberInput.textProperty().bindBidirectional(viewModel.phoneNumberProperty());
		emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
		birthdayInput.valueProperty().bindBidirectional(viewModel.birthdayProperty());

		validationVisualization.visualize(viewModel.firstnameValidation(), firstnameInput);
		validationVisualization.visualize(viewModel.lastnameValidation(), lastnameInput);
		validationVisualization.visualize(viewModel.birthdayValidation(), birthdayInput);
		validationVisualization.visualize(viewModel.emailValidation(), emailInput);
	}
	
	public ContactFormViewModel getViewModel() {
		return viewModel;
	}
	
}
