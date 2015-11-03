package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

import java.util.ResourceBundle;

import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class EditContactDialogViewModel implements ViewModel {
	static final String TITLE_LABEL_KEY = "dialog.editcontact.title";
	
	private final BooleanProperty dialogOpen = new SimpleBooleanProperty();
	
	@Inject
	Repository repository;
	
	@InjectScope
	private ContactDialogScope dialogScope;
	
	@Inject
	ResourceBundle defaultResourceBundle;
	
	private ContactDialogViewModel contactDialogViewModel;
	
	
	public void setContactDialogViewModel(ContactDialogViewModel contactDialogViewModel) {
		this.contactDialogViewModel = contactDialogViewModel;
		
		contactDialogViewModel.setOkAction(this::applyAction);
		contactDialogViewModel.titleTextProperty().set(defaultResourceBundle.getString(TITLE_LABEL_KEY));
		
		dialogOpen.addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				contactDialogViewModel.resetDialogPage();
			}
		});
	}
	
	public void applyAction() {
		if (contactDialogViewModel.validProperty().get()) {
			
			// contactDialogViewModel.getAddressFormViewModel().commitChanges();
			dialogScope.publish(ContactDialogScope.Notifications.COMMIT.toString());
			
			repository.save(dialogScope.contactToEditProperty().get());
			
			dialogOpen.set(false);
		}
	}
	
	
	public void openDialog(String contactId) {
		dialogScope.publish(ContactDialogScope.Notifications.RESET_FORMS.toString());
		
		repository.findById(contactId).ifPresent(contact -> {
			dialogScope.setContactToEdit(contact);
			dialogOpen.set(true);
		});
	}
	
	public BooleanProperty dialogOpenProperty() {
		return dialogOpen;
	}
	
	
	
}
