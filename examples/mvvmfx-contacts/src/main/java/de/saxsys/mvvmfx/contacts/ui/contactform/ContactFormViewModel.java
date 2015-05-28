package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.validation.BirthdayValidator;
import de.saxsys.mvvmfx.contacts.model.validation.EmailAddressValidator;
import de.saxsys.mvvmfx.contacts.model.validation.PhoneNumberValidator;
import de.saxsys.mvvmfx.utils.mapping.ModelWrapper;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import de.saxsys.mvvmfx.utils.validation.Validator;
import javafx.beans.property.*;
import javafx.scene.control.Control;

import java.time.LocalDate;
import java.util.function.Function;

public class ContactFormViewModel implements ViewModel {
	
	private ModelWrapper<Contact> contactWrapper = new ModelWrapper<>();
	
	private Validator<String> firstnameValidator = new Validator<>(firstnameProperty());
	private Validator<String> lastnameValidator = new Validator<>(firstnameProperty());
	private Validator<LocalDate> birthdayValidator= new Validator<>(birthdayProperty());

	public ContactFormViewModel() {
		final Function<String, ValidationMessage> notEmptyValidator = input -> {
			if (input == null || input.trim().isEmpty()) {
				return ValidationMessage.error("The input may not be empty");
			}

			return null;
		};
		
		firstnameValidator.addRule(notEmptyValidator);
		lastnameValidator.addRule(notEmptyValidator);
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
	
	public ValidationResult firstnameValidation() {
		return firstnameValidator.getValidationResult();
	}
	
	public ValidationResult lastnameValidation() {
		return lastnameValidator.getValidationResult();
	}
	
	public ValidationResult birthdayValidation() {
		return birthdayValidator.getValidationResult();
	}
	
	public StringProperty firstnameProperty() {
		return contactWrapper.field("firstname", Contact::getFirstname, Contact::setFirstname);
	}
	
	public StringProperty titleProperty() {
		return contactWrapper.field("title", Contact::getTitle, Contact::setTitle);
	}
	
	public StringProperty lastnameProperty() {
		return contactWrapper.field("lastname", Contact::getLastname, Contact::setLastname);
	}
	
	public StringProperty roleProperty() {
		return contactWrapper.field("role", Contact::getRole, Contact::setRole);
	}
	
	public StringProperty departmentProperty() {
		return contactWrapper.field("department", Contact::getDepartment, Contact::setDepartment);
	}
	
	public Property<LocalDate> birthdayProperty() {
		return contactWrapper.field("birthday", Contact::getBirthday, Contact::setBirthday);
	}
	
	public StringProperty emailProperty() {
		return contactWrapper.field("email", Contact::getEmailAddress, Contact::setEmailAddress);
	}
	
	public StringProperty mobileNumberProperty() {
		return contactWrapper.field("mobileNumber", Contact::getMobileNumber, Contact::setMobileNumber);
	}
	
	public StringProperty phoneNumberProperty() {
		return contactWrapper.field("phoneNumber", Contact::getPhoneNumber, Contact::setPhoneNumber);
	}
}
