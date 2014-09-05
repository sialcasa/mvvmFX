package de.saxsys.mvvmfx.contacts.ui.editcontact;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormViewModel;
import de.saxsys.mvvmfx.contacts.ui.master.MasterViewModel;

public class EditContactDialogViewModel implements ViewModel{
	
	private BooleanProperty dialogOpen = new SimpleBooleanProperty();
	
	private ReadOnlyBooleanWrapper applyButtonDisabled = new ReadOnlyBooleanWrapper();

	private ContactFormViewModel contactFormViewModel;

	@Inject
	MasterViewModel masterViewModel;
	
	@Inject
	Repository repository;

	public void initContactFormViewModel(ContactFormViewModel contactFormViewModel){
		this.contactFormViewModel = contactFormViewModel;
		applyButtonDisabled.bind(contactFormViewModel.validProperty().not());
	}
	
	public void openDialog(String contactId) {
		repository.findById(contactId).ifPresent(contact -> {
			contactFormViewModel.initWithContact(contact);
			dialogOpen.set(true);
		});
	}
	
	public BooleanProperty dialogOpenProperty(){
		return dialogOpen; 
	}

	public ReadOnlyBooleanProperty applyButtonDisabledProperty(){
		return applyButtonDisabled.getReadOnlyProperty();
	}

	public void applyAction() {
		if(contactFormViewModel.validProperty().get()){
			
			repository.save(contactFormViewModel.getContact());
			
			
			
			
			dialogOpen.set(false);
		}
	}
}
