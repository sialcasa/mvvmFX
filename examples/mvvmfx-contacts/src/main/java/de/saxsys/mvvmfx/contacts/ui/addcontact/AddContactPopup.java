package de.saxsys.mvvmfx.contacts.ui.addcontact;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.contacts.events.OpenAddContactPopupEvent;

@Singleton
public class AddContactPopup  implements FxmlView<AddContactPopupViewModel> {
	
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
	
	@FXML
	public Button addContactButton;
	
	@Inject
	private Stage primaryStage;
	
	private Stage popupStage = new Stage(StageStyle.UTILITY);
	
	
	@InjectViewModel
	private AddContactPopupViewModel viewModel;
	
	
	private Parent root;
	
	AddContactPopup() {
		ViewTuple<AddContactPopup, AddContactPopupViewModel> viewTuple = FluentViewLoader.fxmlView(this.getClass())
				.codeBehind(this).load();
		
		root = viewTuple.getView();
	}
	
	public void initialize(){
		popupStage.initOwner(primaryStage);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		
		
		firstnameInput.textProperty().bindBidirectional(viewModel.firstnameProperty());
		lastnameInput.textProperty().bindBidirectional(viewModel.lastnameProperty());
		titleInput.textProperty().bindBidirectional(viewModel.titleProperty());
		roleInput.textProperty().bindBidirectional(viewModel.roleProperty());
		departmentInput.textProperty().bindBidirectional(viewModel.departmentProperty());
		mobileNumberInput.textProperty().bindBidirectional(viewModel.mobileNumberProperty());
		phoneNumberInput.textProperty().bindBidirectional(viewModel.phoneNumberProperty());
		emailInput.textProperty().bindBidirectional(viewModel.emailProperty());
		birthdayInput.valueProperty().bindBidirectional(viewModel.birthdayProperty());
		
		
		viewModel.popupOpenProperty().addListener((obs, oldValue, newValue)->{
			// The handling of the popup stage is view specific so it has to be done here and can't be done in the VM
			if (newValue) {
				if (popupStage.getScene() == null) { // When the popup is shown the first time
					
					popupStage.setScene(new Scene(root));
				} else {
					popupStage.toFront();
				}
				
				popupStage.show();
			} else {
				popupStage.close();
			}
		});
		
		// when the popup is closed by the close-button of the window we change the state in the VM
		popupStage.setOnCloseRequest(event-> viewModel.popupOpenProperty().set(false));
		
		
		// init the validation. The logic for the actual validation is hidden in the viewModel.
		viewModel.initValidationForFirstname(firstnameInput);
		viewModel.initValidationForLastname(lastnameInput);
		viewModel.initValidationForBirthday(birthdayInput);

		viewModel.initValidationForEmail(emailInput);
		viewModel.initValidationForPhoneNumber(phoneNumberInput);
		viewModel.initValidationForMobileNumber(mobileNumberInput);
		
		addContactButton.disableProperty().bind(viewModel.addButtonDisabledProperty());


		AwesomeDude.setIcon(addContactButton, AwesomeIcon.CHECK);
	}
	
	@FXML
	public void addContact(){
		viewModel.addContactAction();
	}
	
	public void open(@Observes OpenAddContactPopupEvent event) {
		viewModel.openDialog();
	}
}
