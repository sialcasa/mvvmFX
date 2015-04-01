package de.saxsys.mvvmfx.contacts.ui.editcontact;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.contacts.events.OpenEditContactDialogEvent;
import de.saxsys.mvvmfx.contacts.ui.contactdialog.ContactDialogView;
import de.saxsys.mvvmfx.contacts.util.DialogHelper;

@Singleton
public class EditContactDialog implements FxmlView<EditContactDialogViewModel> {
	
	@FXML
	private ContactDialogView contactDialogViewController;
	
	
	private Parent root;
	
	@Inject
	private Stage primaryStage;
	
	@InjectViewModel
	private EditContactDialogViewModel viewModel;
	
	@Inject
	EditContactDialog(ResourceBundle defaultResourceBundle) {
		ViewTuple<EditContactDialog, EditContactDialogViewModel> viewTuple = FluentViewLoader.fxmlView(EditContactDialog.class)
				.codeBehind(this).resourceBundle(defaultResourceBundle).load();
		
		root = viewTuple.getView();
	}
	
	public void initialize() {
		viewModel.setContactDialogViewModel(contactDialogViewController.getViewModel());
		
		DialogHelper.initDialog(viewModel.dialogOpenProperty(), primaryStage, () -> root);
	}
	
	public void open(@Observes OpenEditContactDialogEvent event) {
		viewModel.openDialog(event.getContactId());
	}
}
