package de.saxsys.mvvmfx.contacts.ui.addcontact;

import de.saxsys.mvvmfx.contacts.util.DialogHelper;
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

		DialogHelper.initDialog(viewModel.popupOpenProperty(), primaryStage, ()-> root);
		
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
