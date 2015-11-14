package de.saxsys.mvvmfx.examples.contacts.ui.addcontact;

import java.util.ResourceBundle;

import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;

public class AddContactDialogViewModel implements ViewModel {
	
	public static final String CLOSE_DIALOG_NOTIFICATION = "closeDialog";
	
	static final String TITLE_LABEL_KEY = "dialog.addcontact.title";
	
	@Inject
	private Repository repository;
	
	@InjectScope
	private ContactDialogScope dialogScope;
	
	@Inject
	private ResourceBundle defaultResourceBundle;
	
	public void initialize() {
		dialogScope.subscribe(ContactDialogScope.Notifications.OK_BEFORE_COMMIT.toString(), (key, payload) -> {
			addContactAction();
		});
		
		dialogScope.dialogTitleProperty().set(defaultResourceBundle.getString(TITLE_LABEL_KEY));
		dialogScope.publish(ContactDialogScope.Notifications.RESET_FORMS.toString());
		Contact contact = new Contact();
		dialogScope.setContactToEdit(contact);
	}
	
	public void addContactAction() {
		if (dialogScope.isContactFormValid()) {
			
			dialogScope.publish(ContactDialogScope.Notifications.COMMIT.toString());
			
			Contact contact = dialogScope.getContactToEdit();
			
			repository.save(contact);
			
			dialogScope.publish(ContactDialogScope.Notifications.RESET_DIALOG_PAGE.toString());
			dialogScope.setContactToEdit(null);
			
			publish(CLOSE_DIALOG_NOTIFICATION);
		}
	}
}
