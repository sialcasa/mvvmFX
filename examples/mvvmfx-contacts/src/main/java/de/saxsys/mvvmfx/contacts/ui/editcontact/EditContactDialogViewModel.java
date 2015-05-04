package de.saxsys.mvvmfx.contacts.ui.editcontact;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.contactdialog.ContactDialogViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class EditContactDialogViewModel implements ViewModel {
	static final String TITLE_LABEL_KEY = "dialog.editcontact.title";
	
	private BooleanProperty dialogOpen = new SimpleBooleanProperty();
	
	@Inject
	Repository repository;
	
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
			
			contactDialogViewModel.getAddressFormViewModel().commitChanges();
			repository.save(contactDialogViewModel.getContactFormViewModel().getContact());
			
			dialogOpen.set(false);
		}
	}
	
	
	public void openDialog(String contactId) {
		contactDialogViewModel.resetForms();
		repository.findById(contactId).ifPresent(contact -> {
			contactDialogViewModel.getContactFormViewModel().initWithContact(contact);
			contactDialogViewModel.getAddressFormViewModel().initWithAddress(contact.getAddress());
			dialogOpen.set(true);
		});
	}
	
	public BooleanProperty dialogOpenProperty() {
		return dialogOpen;
	}
	
	
	
}
