package de.saxsys.mvvmfx.contacts.ui.addcontact;

import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.contactdialog.ContactDialogViewModel;

public class AddContactDialogViewModel implements ViewModel {
	private BooleanProperty dialogOpen = new SimpleBooleanProperty();
	
	@Inject
	private Repository repository;
	
	private ContactDialogViewModel contactDialogViewModel;

	@Inject
	private ResourceBundle defaultResourceBundle;
	
	public AddContactDialogViewModel(){
		dialogOpen.addListener((obs, oldV, newV)->{
			if(!newV) {
				contactDialogViewModel.resetDialogPage();
			}
		});
	}
	
	
	public void setContactDialogViewModel(ContactDialogViewModel contactDialogViewModel){
		this.contactDialogViewModel = contactDialogViewModel;
		
		contactDialogViewModel.setOkAction(this::addContactAction);
		contactDialogViewModel.titleTextProperty().set(defaultResourceBundle.getString("dialog.addcontact.title"));
	}
		
	
	public void addContactAction() {
		if (contactDialogViewModel.validProperty().get()) {
			// Add logic to persist the new contact.
			
			Contact contact = contactDialogViewModel.getContactFormViewModel().getContact();
			
			repository.save(contact);
			
			dialogOpen.set(false);
		}
	}
	
	public void openDialog() {
		contactDialogViewModel.getContactFormViewModel().resetForm();
		
		this.dialogOpenProperty().set(true);
	}
	
	
	public BooleanProperty dialogOpenProperty() {
		return dialogOpen;
	}
}
