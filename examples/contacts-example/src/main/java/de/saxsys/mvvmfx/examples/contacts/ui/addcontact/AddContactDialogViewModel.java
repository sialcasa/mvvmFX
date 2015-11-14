package de.saxsys.mvvmfx.examples.contacts.ui.addcontact;

import java.util.ResourceBundle;

import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AddContactDialogViewModel implements ViewModel {
	
	public static final String CLOSE_DIALOG_NOTIFICATION = "closeDialog";
	
	static final String TITLE_LABEL_KEY = "dialog.addcontact.title";
	
	private final BooleanProperty dialogOpen = new SimpleBooleanProperty();
	@Inject
	private Repository repository;
	
	@InjectScope
	private ContactDialogScope dialogScope;
	
	@Inject
	private ResourceBundle defaultResourceBundle;
	
	public AddContactDialogViewModel() {
		dialogOpen.addListener((obs, oldV, newV) -> {
			if (!newV) {
				// contactDialogViewModel.resetDialogPage();
				dialogScope.publish(ContactDialogScope.Notifications.RESET_DIALOG_PAGE.toString());
				dialogScope.setContactToEdit(null);
			}
		});
	}
	
	public void initialize() {
		openDialog();
	}
	
	
	public void setContactDialogViewModel(ContactDialogViewModel contactDialogViewModel) {
		contactDialogViewModel.setOkAction(this::addContactAction);
		contactDialogViewModel.titleTextProperty().set(defaultResourceBundle.getString(TITLE_LABEL_KEY));
	}
	
	
	public void addContactAction() {
		if (dialogScope.isContactFormValid()) {
			
			// contactDialogViewModel.getAddressFormViewModel().commitChanges();
			dialogScope.publish(ContactDialogScope.Notifications.COMMIT.toString());
			
			Contact contact = dialogScope.getContactToEdit();
			
			repository.save(contact);
			
			publish(CLOSE_DIALOG_NOTIFICATION);
		}
	}
	
	public void openDialog() {
		dialogScope.publish(ContactDialogScope.Notifications.RESET_FORMS.toString());
		
		Contact contact = new Contact();
		dialogScope.setContactToEdit(contact);
	}
	
	
}
