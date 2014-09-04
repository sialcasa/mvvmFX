package de.saxsys.mvvmfx.contacts.ui.addcontact;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormViewModel;

public class AddContactDialogViewModel implements ViewModel {
	private BooleanProperty popupOpen = new SimpleBooleanProperty();
	
	private ReadOnlyBooleanWrapper addButtonDisabled = new ReadOnlyBooleanWrapper();
	
	@Inject
	private Repository repository;
	
	private ContactFormViewModel contactFormViewModel;

	public void initContactFormViewModel(ContactFormViewModel contactFormViewModel){
		this.contactFormViewModel = contactFormViewModel;
		addButtonDisabled.bind(contactFormViewModel.validProperty().not());
		
	}
	
	public void addContactAction() {
		if (contactFormViewModel.validProperty().get()) {
			// Add logic to persist the new contact.
			
			Contact contact = contactFormViewModel.getContact();
			
			repository.save(contact);
			
			popupOpen.set(false);
		}
	}
	
	public void openDialog() {
		contactFormViewModel.resetForm();
		this.dialogOpenProperty().set(true);
	}
	
	
	public BooleanProperty dialogOpenProperty() {
		return popupOpen;
	}
	
	
	public ReadOnlyBooleanProperty addButtonDisabledProperty() {
		return addButtonDisabled.getReadOnlyProperty();
	}
	
	
	
}
