package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.validation.BirthdayValidator;
import de.saxsys.mvvmfx.contacts.model.validation.EmailAddressValidator;
import de.saxsys.mvvmfx.contacts.model.validation.PhoneNumberValidator;
import de.saxsys.mvvmfx.utils.mapping.ModelWrapper;
import javafx.beans.property.*;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.time.LocalDate;

public class ContactFormViewModel implements ViewModel {
	
	private ModelWrapper<Contact> contactWrapper = new ModelWrapper<>();

	private ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper();
	
	
	ValidationSupport validationSupport = new ValidationSupport();

	public ContactFormViewModel() {
		valid.bind(validationSupport.invalidProperty().isNull().or(validationSupport.invalidProperty().isEqualTo
				(false)));
		
	}
	
	public void resetForm() {
		contactWrapper.reset();
	}
	
	public void initWithContact(Contact contact) {
		this.contactWrapper.set(contact);
		this.contactWrapper.reload();
	}
	
	public Contact getContact() {

		if(contactWrapper.get() == null) {
			contactWrapper.set(new Contact());
		}

		contactWrapper.commit();

		return contactWrapper.get();
	}
	
	public void initValidationForFirstname(Control input) {
		validationSupport.registerValidator(input, Validator.createEmptyValidator("Firstname may not be empty!"));
	}
	
	public void initValidationForLastname(Control input) {
		validationSupport.registerValidator(input, Validator.createEmptyValidator("Lastname may not be empty!"));
	}
	
	public void initValidationForBirthday(Control input) {
		validationSupport.registerValidator(input, false, new BirthdayValidator());
	}
	
	public void initValidationForEmail(Control input) {
		validationSupport.registerValidator(input, true, new EmailAddressValidator());
	}
	
	public void initValidationForPhoneNumber(Control input) {
		validationSupport.registerValidator(input, false, new PhoneNumberValidator("The phone number is invalid!"));
	}
	
	public void initValidationForMobileNumber(Control input) {
		validationSupport.registerValidator(input, false, new PhoneNumberValidator("The mobile number is invalid!"));
	}
	
	public ReadOnlyBooleanProperty validProperty() {
		return valid.getReadOnlyProperty();
	}
	
	
	
	public Property<String> firstnameProperty() {
		return contactWrapper.field("firstname", Contact::getFirstname, Contact::setFirstname);
	}
	
	public Property<String> titleProperty() {
		return contactWrapper.field("title", Contact::getTitle, Contact::setTitle);
	}
	
	public Property<String> lastnameProperty() {
		return contactWrapper.field("lastname", Contact::getLastname, Contact::setLastname);
	}
	
	public Property<String> roleProperty() {
		return contactWrapper.field("role", Contact::getRole, Contact::setRole);
	}
	
	public Property<String> departmentProperty() {
		return contactWrapper.field("department", Contact::getDepartment, Contact::setDepartment);
	}
	
	public Property<LocalDate> birthdayProperty() {
		return contactWrapper.field("birthday", Contact::getBirthday, Contact::setBirthday);
	}
	
	public Property<String> emailProperty() {
		return contactWrapper.field("email", Contact::getEmailAddress, Contact::setEmailAddress);
	}
	
	public Property<String> mobileNumberProperty() {
		return contactWrapper.field("mobileNumber", Contact::getMobileNumber, Contact::setMobileNumber);
	}
	
	public Property<String> phoneNumberProperty() {
		return contactWrapper.field("phoneNumber", Contact::getPhoneNumber, Contact::setPhoneNumber);
	}
}
