package de.saxsys.mvvmfx.contacts.ui.editcontact;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.contacts.events.OpenEditContactDialogEvent;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormView;
import de.saxsys.mvvmfx.contacts.util.DialogHelper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableBooleanValue;
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
import java.util.ResourceBundle;
import java.util.function.Supplier;

@Singleton
public class EditContactDialog implements FxmlView<EditContactDialogViewModel> {
	
	
	private Parent root;
	
	@FXML
	public Button applyButton;
	
	@Inject
	private Stage primaryStage;

	@FXML
	private ContactFormView contactFormViewController;
	
	@InjectViewModel
	private EditContactDialogViewModel viewModel;
	
	@Inject
	EditContactDialog(ResourceBundle defaultResourceBundle) {
		ViewTuple<EditContactDialog, EditContactDialogViewModel> viewTuple = FluentViewLoader.fxmlView(this.getClass())
				.codeBehind(this).resourceBundle(defaultResourceBundle).load();
		
		root = viewTuple.getView();
	}
	
	public void initialize(){
		viewModel.initContactFormViewModel(contactFormViewController.getViewModel());
		
		
		DialogHelper.initDialog(viewModel.dialogOpenProperty(), primaryStage, () -> root);
		
		applyButton.disableProperty().bind(viewModel.applyButtonDisabledProperty());
	}
	
	@FXML
	public void apply(){
		viewModel.applyAction();
	}
	
	public void open(@Observes OpenEditContactDialogEvent event){
		viewModel.openDialog(event.getContactId());
	}
}
