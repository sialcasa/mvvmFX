package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

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
	
	@InjectViewModel
	private EditContactDialogViewModel viewModel;
	
	private Stage showDialog;
	
	public void initialize() {
		viewModel.subscribe(EditContactDialogViewModel.CLOSE_DIALOG_NOTIFICATION, (key, payload) -> {
			showDialog.close();
		});
	}
	
	public void setOwningStage(Stage showDialog) {
		this.showDialog = showDialog;
	}
}
