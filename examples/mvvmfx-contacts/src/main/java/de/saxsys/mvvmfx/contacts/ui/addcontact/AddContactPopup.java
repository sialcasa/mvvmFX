package de.saxsys.mvvmfx.contacts.ui.addcontact;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.contacts.events.OpenAddContactPopupEvent;

@Singleton
public class AddContactPopup extends VBox implements FxmlView<AddContactPopupViewModel> {
	
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
	
	
	@Inject
	private Stage primaryStage;
	
	private Stage popupStage = new Stage(StageStyle.UTILITY);
	
	
	@InjectViewModel
	private AddContactPopupViewModel viewModel;
	
	public AddContactPopup() {
		FluentViewLoader.fxmlView(this.getClass()).root(this).codeBehind(this).load();
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
					popupStage.setScene(new Scene(this));
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
	}
	
	@FXML
	public void addContact(){
		viewModel.addContactAction();
	}
	
	public void open(@Observes OpenAddContactPopupEvent event) {
		viewModel.openDialog();
	}
}
