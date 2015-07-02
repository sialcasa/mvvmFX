package de.saxsys.mvvmfx.examples.contacts.ui.addcontact;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.contacts.events.OpenAddContactDialogEvent;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogView;
import de.saxsys.mvvmfx.examples.contacts.util.DialogHelper;

@Singleton
public class AddContactDialog implements FxmlView<AddContactDialogViewModel> {
	
	
	@FXML
	private ContactDialogView contactDialogViewController;
	
	
	@Inject
	private Stage primaryStage;
	
	@InjectViewModel
	private AddContactDialogViewModel viewModel;
	
	private Parent root;
	
	@Inject
	AddContactDialog() {
		root = FluentViewLoader
				.fxmlView(AddContactDialog.class)
				.codeBehind(this)
				.load()
				.getView();
	}
	
	public void initialize() {
		viewModel.setContactDialogViewModel(contactDialogViewController.getViewModel());
		
		DialogHelper.initDialog(viewModel.dialogOpenProperty(), primaryStage, () -> root);
	}
	
	public void open(@Observes OpenAddContactDialogEvent event) {
		viewModel.openDialog();
	}
}
