package de.saxsys.mvvmfx.contacts.ui.addcontact;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Pagination;
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
import de.saxsys.mvvmfx.contacts.events.OpenAddContactDialogEvent;
import de.saxsys.mvvmfx.contacts.ui.addressform.AddressFormView;
import de.saxsys.mvvmfx.contacts.ui.addressform.AddressFormViewModel;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormView;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormViewModel;
import de.saxsys.mvvmfx.contacts.util.DialogHelper;

@Singleton
public class AddContactDialog implements FxmlView<AddContactDialogViewModel> {
	
	@FXML
	public Button addContactButton;
	
	@Inject
	private Stage primaryStage;
	
	@FXML
	private Pagination formPagination;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Button previousButton;
	
	
	@InjectViewModel
	private AddContactDialogViewModel viewModel;
	
	private ResourceBundle defaultResourceBundle;
	
	private Parent root;
	
	@Inject
	AddContactDialog(ResourceBundle defaultResourceBundle) {
		this.defaultResourceBundle = defaultResourceBundle;
		ViewTuple<AddContactDialog, AddContactDialogViewModel> viewTuple = FluentViewLoader.fxmlView(this.getClass())
				.codeBehind(this).resourceBundle(defaultResourceBundle).load();
		
		root = viewTuple.getView();
	}
	
	public void initialize() {
		ViewTuple<ContactFormView, ContactFormViewModel> contactFormTuple = FluentViewLoader.fxmlView(ContactFormView.class)
				.resourceBundle(defaultResourceBundle).load();

		ViewTuple<AddressFormView, AddressFormViewModel> addressFormTuple = FluentViewLoader.fxmlView(AddressFormView.class)
				.resourceBundle(defaultResourceBundle).load();
		
		viewModel.initContactFormViewModel(contactFormTuple.getViewModel());
		viewModel.initAddressFormViewModel(addressFormTuple.getViewModel());

		formPagination.getStyleClass().add("invisible-pagination-control");
		
		formPagination.setPageFactory(index -> {
			if (index == 0) {
				return contactFormTuple.getView();
			} else {
				return addressFormTuple.getView();
			}
		});
		
		formPagination.currentPageIndexProperty().bindBidirectional(viewModel.dialogPageProperty());

		DialogHelper.initDialog(viewModel.dialogOpenProperty(), primaryStage, ()-> root);
		
		
		AwesomeDude.setIcon(addContactButton, AwesomeIcon.CHECK);
		AwesomeDude.setIcon(nextButton, AwesomeIcon.CHEVRON_RIGHT, ContentDisplay.RIGHT);
		AwesomeDude.setIcon(previousButton, AwesomeIcon.CHEVRON_LEFT);
		
		addContactButton.disableProperty().bind(viewModel.addButtonDisabledProperty());
		addContactButton.visibleProperty().bind(viewModel.addButtonVisibleProperty());
		addContactButton.managedProperty().bind(viewModel.addButtonVisibleProperty());
		
		nextButton.disableProperty().bind(viewModel.nextButtonDisabledProperty());
		nextButton.visibleProperty().bind(viewModel.nextButtonVisibleProperty());
		nextButton.managedProperty().bind(viewModel.nextButtonVisibleProperty());
		
		previousButton.disableProperty().bind(viewModel.previousButtonDisabledProperty());
		previousButton.visibleProperty().bind(viewModel.previousButtonVisibleProperty());
		previousButton.managedProperty().bind(viewModel.previousButtonVisibleProperty());
	}
	
	@FXML
	public void next(){
		viewModel.nextAction();
	}
	
	@FXML
	public void previous(){
		viewModel.previousAction();
	}
	
	@FXML
	public void addContact() {
		viewModel.addContactAction();
	}
	
	public void open(@Observes OpenAddContactDialogEvent event) {
		viewModel.openDialog();
	}
}
