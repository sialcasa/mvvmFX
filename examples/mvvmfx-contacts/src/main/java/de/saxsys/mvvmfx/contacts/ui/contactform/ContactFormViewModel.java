package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.utils.mapping.ModelWrapper;
import de.saxsys.mvvmfx.utils.validation.Rules;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import de.saxsys.mvvmfx.utils.validation.Validator;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ContactFormViewModel implements ViewModel {
    private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern
            .compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	
	private ModelWrapper<Contact> contactWrapper = new ModelWrapper<>();
	
	private Validator firstnameValidator = new Validator();
	private Validator lastnameValidator = new Validator();
	private Validator emailValidator = new Validator();
	private Validator birthdayValidator= new Validator();

	public ContactFormViewModel() {
		firstnameValidator.addRule(Rules.notEmpty(firstnameProperty()), ValidationMessage.error("Firstname nicht leer."));
        lastnameValidator.addRule(Rules.notEmpty(lastnameProperty()), ValidationMessage.error("Lastname nicht leer."));

        emailValidator.addRule(Rules.notEmpty(emailProperty()), ValidationMessage.error("Email may not be empty"));
        emailValidator.addRule(Rules.matches(emailProperty(), SIMPLE_EMAIL_PATTERN), ValidationMessage.error("Wrong email format"));
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

    public ValidationResult emailValidation() {
        return emailValidator.getValidationResult();
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
        return firstnameValidator.getValidationResult().validProperty().and(
				lastnameValidator.getValidationResult().validProperty());
    }
}
