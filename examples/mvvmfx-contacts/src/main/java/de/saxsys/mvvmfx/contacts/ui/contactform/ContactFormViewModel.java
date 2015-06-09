package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.validation.BirthdayValidator;
import de.saxsys.mvvmfx.contacts.model.validation.EmailValidator;
import de.saxsys.mvvmfx.contacts.model.validation.PhoneValidator;
import de.saxsys.mvvmfx.utils.mapping.ModelWrapper;
import de.saxsys.mvvmfx.utils.validation.*;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class ContactFormViewModel implements ViewModel {
	private ModelWrapper<Contact> contactWrapper = new ModelWrapper<>();
	
	private FunctionBasedValidator<String> firstnameValidator;
	private FunctionBasedValidator<String> lastnameValidator;
	private ObservableRuleBasedValidator emailValidator = new EmailValidator(emailProperty());
	private ObservableRuleBasedValidator birthdayValidator = new BirthdayValidator(birthdayProperty());
	
	private ObservableRuleBasedValidator phoneValidator = new PhoneValidator(phoneNumberProperty(), "The phone number is invalid!");
	private ObservableRuleBasedValidator mobileValidator = new PhoneValidator(mobileNumberProperty(), "The mobile number is invalid!");

	private CompositeValidator formValidator = new CompositeValidator();
	
	public ContactFormViewModel() {
        firstnameValidator = new FunctionBasedValidator<>(
                firstnameProperty(),
                firstName -> firstName != null && !firstName.trim().isEmpty(),
                ValidationMessage.error("Firstname may not be empty"));


        lastnameValidator = new FunctionBasedValidator<>(lastnameProperty(), lastName -> {
            if(lastName == null || lastName.isEmpty()) {
                return ValidationMessage.error("Lastname may not be empty");
            } else if(lastName.trim().isEmpty()) {
                return ValidationMessage.error("Lastname may not only contain whitespaces");
            }

            return null;
        });


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
