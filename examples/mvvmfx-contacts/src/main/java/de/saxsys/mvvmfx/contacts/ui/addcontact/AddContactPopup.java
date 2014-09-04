package de.saxsys.mvvmfx.contacts.ui.addcontact;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.contacts.events.OpenAddContactPopupEvent;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormView;

@Singleton
public class AddContactPopup implements FxmlView<AddContactPopupViewModel> {
	
	@FXML
	public Button addContactButton;
	
	@Inject
	private Stage primaryStage;
	
	private Stage popupStage = new Stage(StageStyle.UTILITY);
	
	@FXML
	private ContactFormView contactFormViewController;
	
	
	@InjectViewModel
	private AddContactPopupViewModel viewModel;
	
	
	private Parent root;
	
	AddContactPopup() {
		ViewTuple<AddContactPopup, AddContactPopupViewModel> viewTuple = FluentViewLoader.fxmlView(this.getClass())
				.codeBehind(this).load();
		
		root = viewTuple.getView();
		
	}
	
	public void initialize() {
		viewModel.initContactFormViewModel(contactFormViewController.getViewModel());
		
		popupStage.initOwner(primaryStage);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		
		viewModel.popupOpenProperty().addListener((obs, oldValue, newValue) -> {
			// The handling of the popup stage is view specific so it has to be done here and can't be done in the VM
				if (newValue) {
					if (popupStage.getScene() == null) { // When the popup is shown the first time
					
						popupStage.setScene(new Scene(root));
						popupStage.sizeToScene();
					} else {
						popupStage.toFront();
					}
					
					popupStage.show();
				} else {
					popupStage.close();
				}
			});
		
		// when the popup is closed by the close-button of the window we change the state in the VM
		popupStage.setOnCloseRequest(event -> viewModel.popupOpenProperty().set(false));
		
		addContactButton.disableProperty().bind(viewModel.addButtonDisabledProperty());
		
		AwesomeDude.setIcon(addContactButton, AwesomeIcon.CHECK);
	}
	
	@FXML
	public void addContact() {
		viewModel.addContactAction();
	}
	
	public void open(@Observes OpenAddContactPopupEvent event) {
		viewModel.openDialog();
	}
}
