package de.saxsys.mvvmfx.contacts.ui.addcontact;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.enterprise.event.Observes;
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
	
	
	private Stage popupStage = new Stage(StageStyle.UTILITY);
	
	
	@InjectViewModel
	private AddContactPopupViewModel viewModel;
	
	public AddContactPopup() {
		FluentViewLoader.fxmlView(this.getClass()).root(this).codeBehind(this).load();
	}
	
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
	}
	
	public void open(@Observes OpenAddContactPopupEvent event, Stage primaryStage) {
		
		viewModel.resetForm();
		
		if (popupStage.getScene() == null) { // When the popup is shown the first time
			popupStage.initOwner(primaryStage);
			popupStage.setScene(new Scene(this));
		} else {
			popupStage.toFront();
		}
		
		popupStage.show();
		
	}
}
