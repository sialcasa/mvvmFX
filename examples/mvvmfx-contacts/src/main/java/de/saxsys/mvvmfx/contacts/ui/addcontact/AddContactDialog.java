package de.saxsys.mvvmfx.contacts.ui.addcontact;

import de.saxsys.mvvmfx.contacts.events.OpenAddContactDialogEvent;
import de.saxsys.mvvmfx.contacts.util.DialogHelper;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormView;

@Singleton
public class AddContactDialog implements FxmlView<AddContactDialogViewModel> {
	
	@FXML
	public Button addContactButton;
	
	@Inject
	private Stage primaryStage;
	
	@FXML
	private ContactFormView contactFormViewController;
	
	
	@InjectViewModel
	private AddContactDialogViewModel viewModel;
	
	
	private Parent root;
	
	AddContactDialog() {
		ViewTuple<AddContactDialog, AddContactDialogViewModel> viewTuple = FluentViewLoader.fxmlView(this.getClass())
				.codeBehind(this).load();
		
		root = viewTuple.getView();
		
	}
	
	public void initialize() {
		viewModel.initContactFormViewModel(contactFormViewController.getViewModel());

		DialogHelper.initDialog(viewModel.dialogOpenProperty(), primaryStage, ()-> root);
		
		addContactButton.disableProperty().bind(viewModel.addButtonDisabledProperty());
		
		AwesomeDude.setIcon(addContactButton, AwesomeIcon.CHECK);
	}
	
	@FXML
	public void addContact() {
		viewModel.addContactAction();
	}
	
	public void open(@Observes OpenAddContactDialogEvent event) {
		viewModel.openDialog();
	}
}
