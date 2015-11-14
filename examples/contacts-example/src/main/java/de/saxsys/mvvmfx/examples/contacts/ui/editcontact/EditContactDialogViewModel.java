package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

import java.util.ResourceBundle;

import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;

public class EditContactDialogViewModel implements ViewModel {
	static final String TITLE_LABEL_KEY = "dialog.editcontact.title";
	
	public static final String CLOSE_DIALOG_NOTIFICATION = "CLOSE_DIALOG_NOT";
	
	@Inject
	Repository repository;
	
	@InjectScope
	ContactDialogScope dialogScope;
	
	@Inject
	ResourceBundle defaultResourceBundle;
	
	public void setContactDialogViewModel(ContactDialogViewModel contactDialogViewModel) {
		contactDialogViewModel.setOkAction(this::applyAction);
		contactDialogViewModel.titleTextProperty().set(defaultResourceBundle.getString(TITLE_LABEL_KEY));
		
	}
	
	public void initialze() {
		dialogScope.publish(ContactDialogScope.Notifications.RESET_FORMS.toString());
		dialogScope.publish(ContactDialogScope.Notifications.RESET_DIALOG_PAGE.toString());
		// dialogScope.setContactToEdit(null);
	}
	
	public void applyAction() {
		if (dialogScope.bothFormsValidProperty().get()) {
			
			// contactDialogViewModel.getAddressFormViewModel().commitChanges();
			dialogScope.publish(ContactDialogScope.Notifications.COMMIT.toString());
			
			repository.save(dialogScope.contactToEditProperty().get());
			
			publish(CLOSE_DIALOG_NOTIFICATION);
		}
	}
}
