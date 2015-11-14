package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogView;
import javafx.fxml.FXML;
import javafx.stage.Stage;

@Singleton
public class EditContactDialog implements FxmlView<EditContactDialogViewModel> {
	
	@FXML
	private ContactDialogView contactDialogViewController;
	
	@Inject
	private Stage primaryStage;
	
	@InjectViewModel
	private EditContactDialogViewModel viewModel;
	
	private Stage showDialog;
	
	public void initialize() {
		viewModel.setContactDialogViewModel(contactDialogViewController.getViewModel());
		
		viewModel.subscribe(viewModel.CLOSE_DIALOG_NOTIFICATION, (key, payload) -> {
			showDialog.close();
		});
	}
	
	public void setOwningStage(Stage showDialog) {
		this.showDialog = showDialog;
	}
}
