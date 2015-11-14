package de.saxsys.mvvmfx.examples.contacts.ui.addcontact;

import javax.inject.Singleton;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogView;
import javafx.fxml.FXML;
import javafx.stage.Stage;

@Singleton
public class AddContactDialog implements FxmlView<AddContactDialogViewModel> {
	
	
	@FXML
	private ContactDialogView contactDialogViewController;
	
	@InjectViewModel
	private AddContactDialogViewModel viewModel;
	
	private Stage showDialog;
	
	
	public void initialize() {
		viewModel.subscribe(AddContactDialogViewModel.CLOSE_DIALOG_NOTIFICATION, (key, payload) -> {
			showDialog.close();
		});
	}
	
	
	public void setDisplayingStage(Stage showDialog) {
		this.showDialog = showDialog;
	}
	
}
