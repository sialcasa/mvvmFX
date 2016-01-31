package de.saxsys.mvvmfx.examples.contacts.ui.contactform;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.validation.visualization.ControlsFxVisualizer;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizer;
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

    private ValidationVisualizer validationVisualizer = new ControlsFxVisualizer();

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

        validationVisualizer.initVisualization(viewModel.firstnameValidation(), firstnameInput, true);
        validationVisualizer.initVisualization(viewModel.lastnameValidation(), lastnameInput, true);
        validationVisualizer.initVisualization(viewModel.birthdayValidation(), birthdayInput);
        validationVisualizer.initVisualization(viewModel.emailValidation(), emailInput, true);
        validationVisualizer.initVisualization(viewModel.phoneValidation(), phoneNumberInput);
        validationVisualizer.initVisualization(viewModel.mobileValidation(), mobileNumberInput);
    }

    public ContactFormViewModel getViewModel() {
        return viewModel;
    }

}
