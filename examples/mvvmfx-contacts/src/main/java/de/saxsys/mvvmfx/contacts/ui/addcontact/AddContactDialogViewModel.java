package de.saxsys.mvvmfx.contacts.ui.addcontact;

import de.saxsys.mvvmfx.contacts.ui.addressform.AddressFormViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableBooleanValue;

public class AddContactDialogViewModel implements ViewModel {
	private BooleanProperty dialogOpen = new SimpleBooleanProperty();
	
	private IntegerProperty dialogPage = new SimpleIntegerProperty(0);
	
	@Inject
	private Repository repository;
	
	private ContactFormViewModel contactFormViewModel;
	
	private AddressFormViewModel addressFormViewModel;
	
	public AddContactDialogViewModel(){
		dialogOpen.addListener((obs, oldV, newV)->{
			if(!newV) {
				dialogPage.set(0);
			}
		});
	}

	public void initContactFormViewModel(ContactFormViewModel contactFormViewModel){
		this.contactFormViewModel = contactFormViewModel;
	}
	
	public void initAddressFormViewModel(AddressFormViewModel addressFormViewModel){
		this.addressFormViewModel = addressFormViewModel;
	}
	
	public void nextAction(){
		if(dialogPage.get() == 0){
			dialogPage.set(1);
		}
	}
	
	public void previousAction(){
		if(dialogPage.get() == 1){
			dialogPage.set(0);
		}
	}
	
	public void addContactAction() {
		if (contactFormViewModel.validProperty().get()) {
			// Add logic to persist the new contact.
			
			Contact contact = contactFormViewModel.getContact();
			
			repository.save(contact);
			
			dialogOpen.set(false);
		}
	}
	
	public void openDialog() {
		contactFormViewModel.resetForm();
		this.dialogOpenProperty().set(true);
	}
	
	
	public BooleanProperty dialogOpenProperty() {
		return dialogOpen;
	}
	
	
	public IntegerProperty dialogPageProperty(){
		return dialogPage;
	}


	public ObservableBooleanValue addButtonDisabledProperty() {
		return contactFormViewModel.validProperty().and(addressFormViewModel.validProperty()).not();
	}
	
	public ObservableBooleanValue addButtonVisibleProperty(){
		return dialogPage.isEqualTo(1);
	}
	
	public ObservableBooleanValue nextButtonDisabledProperty(){
		return contactFormViewModel.validProperty().not();
	}
	
	public ObservableBooleanValue nextButtonVisibleProperty(){
		return dialogPage.isEqualTo(0);
	}
	
	public ObservableBooleanValue previousButtonVisibleProperty(){
		return dialogPage.isEqualTo(1);
	}
	
	public ObservableBooleanValue previousButtonDisabledProperty(){
		return addressFormViewModel.validProperty().not();
	}
	
}
