package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.validation.BirthdayValidator;
import de.saxsys.mvvmfx.contacts.model.validation.EmailValidator;
import de.saxsys.mvvmfx.contacts.model.validation.PhoneValidator;
import de.saxsys.mvvmfx.utils.mapping.ModelWrapper;
import de.saxsys.mvvmfx.utils.validation.CompositeValidator;
import de.saxsys.mvvmfx.utils.validation.Rules;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import de.saxsys.mvvmfx.utils.validation.RuleBasedValidator;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class ContactFormViewModel implements ViewModel {
	private ModelWrapper<Contact> contactWrapper = new ModelWrapper<>();
	
	private RuleBasedValidator firstnameValidator = new RuleBasedValidator();
	private RuleBasedValidator lastnameValidator = new RuleBasedValidator();
	private RuleBasedValidator emailValidator = new EmailValidator(emailProperty());
	private RuleBasedValidator birthdayValidator = new BirthdayValidator(birthdayProperty());
	
	private RuleBasedValidator phoneValidator = new PhoneValidator(phoneNumberProperty(), "The phone number is invalid!");
	private RuleBasedValidator mobileValidator = new PhoneValidator(mobileNumberProperty(), "The mobile number is invalid!");

	private CompositeValidator formValidator = new CompositeValidator();
	
	public ContactFormViewModel() {
		firstnameValidator.addRule(Rules.notEmpty(firstnameProperty()), ValidationMessage.error("Firstname nicht leer."));
        lastnameValidator.addRule(Rules.notEmpty(lastnameProperty()), ValidationMessage.error("Lastname nicht leer."));
		
		formValidator.registerValidator(
				firstnameValidator,
				lastnameValidator,
				emailValidator,
				birthdayValidator,
				phoneValidator,
				mobileValidator);
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
		return firstnameValidator.getResult();
	}
	
	public ValidationResult lastnameValidation() {
		return lastnameValidator.getResult();
	}
	
	public ValidationResult birthdayValidation() {
		return birthdayValidator.getResult();
	}

    public ValidationResult emailValidation() {
        return emailValidator.getResult();
    }

	public ValidationResult phoneValidation() {
		return phoneValidator.getResult();
	}
	
	public ValidationResult mobileValidation() {
		return mobileValidator.getResult();
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

    public BooleanExpression validProperty() {
        return formValidator.getResult().validProperty();
    }
}
