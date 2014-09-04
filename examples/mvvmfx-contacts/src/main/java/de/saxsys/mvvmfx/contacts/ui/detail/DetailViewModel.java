package de.saxsys.mvvmfx.contacts.ui.detail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import de.saxsys.mvvmfx.contacts.model.Repository;
import javafx.application.HostServices;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableObjectValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.ui.master.MasterViewModel;

public class DetailViewModel implements ViewModel {
	
	private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ISO_DATE;

	private ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();

	private ReadOnlyStringWrapper birthday = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper roleDepartment = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper email = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper phone = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper mobile = new ReadOnlyStringWrapper();

	private ReadOnlyBooleanWrapper removeButtonEnabled = new ReadOnlyBooleanWrapper();


	@Inject
	MasterViewModel masterViewModel;
	
	@Inject
	HostServices hostServices;

	@Inject
	Repository repository;

	@PostConstruct
	void init(){
		ReadOnlyObjectProperty<Contact> contactProperty = masterViewModel.selectedContactProperty();

		name.bind(extractValue(contactProperty, contact->{
			StringBuilder result = new StringBuilder();

			String title = contact.getTitle();
			if(title != null && !title.trim().isEmpty()){
				result.append(title);
				result.append(" ");
			}

			result.append(contact.getFirstname());
			result.append(" ");
			result.append(contact.getLastname());

			return result.toString();
		}));
		
		email.bind(extractValue(contactProperty, Contact::getEmailAddress));
		
		roleDepartment.bind(extractValue(contactProperty, contact->{
			StringBuilder result = new StringBuilder();
			if(contact.getRole() != null && !contact.getRole().trim().isEmpty()){
				result.append(contact.getRole());

				if(contact.getDepartment() != null && !contact.getDepartment().trim().isEmpty()){
					result.append(" / ");
					result.append(contact.getDepartment());
				}
			}else if(contact.getDepartment() != null){
				result.append(contact.getDepartment());
			}

			return result.toString();
		}));
		
		birthday.bind(extractValue(contactProperty, contact-> {
			LocalDate date = contact.getBirthday();
			if(date == null){
				return "";
			}else{
				return BIRTHDAY_FORMATTER.format(date);
			}
		}));
		
		phone.bind(extractValue(contactProperty, Contact::getPhoneNumber));
		
		mobile.bind(extractValue(contactProperty, Contact::getMobileNumber));


		removeButtonEnabled.bind(masterViewModel.selectedContactProperty().isNotNull());
	}
	
	
	private StringBinding extractValue(ObservableObjectValue<Contact> contactProperty, Function<Contact, String> func){
		return Bindings.createStringBinding(()->{
			Contact contact = contactProperty.get();
			if(contact != null){
				String value = func.apply(contact);
				if(value != null){
					return value;
				}
			}
			
			return "";
		}, contactProperty);
	}
	
	
	public void onEmailLinkClicked(){
		if(email.get() != null && !email.get().trim().isEmpty()){
			hostServices.showDocument("mailto:" + email.get());
		}
	}

	public void editAction() {

	}

	public void removeAction() {
		repository.delete(masterViewModel.selectedContactProperty().get());
	}



	public ReadOnlyStringProperty nameLabelTextProperty() {
		return name.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty birthdayLabelTextProperty(){
		return birthday.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty roleDepartmentLabelTextProperty(){
		return roleDepartment.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty emailLabelTextProperty(){
		return email.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty phoneLabelTextProperty(){
		return phone.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty mobileLabelTextProperty(){
		return mobile.getReadOnlyProperty();
	}

	public ReadOnlyBooleanProperty removeButtonEnabledProperty(){
		return removeButtonEnabled.getReadOnlyProperty();
	}

}
