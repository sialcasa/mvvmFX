package de.saxsys.mvvmfx.examples.contacts.ui.contactform;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import de.saxsys.mvvmfx.examples.contacts.ui.validators.BirthdayValidator;
import de.saxsys.mvvmfx.examples.contacts.ui.validators.EmailValidator;
import de.saxsys.mvvmfx.examples.contacts.ui.validators.PhoneValidator;
import de.saxsys.mvvmfx.utils.mapping.ModelWrapper;
import de.saxsys.mvvmfx.utils.validation.*;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class ContactFormViewModel implements ViewModel {
	private final ModelWrapper<Contact> contactWrapper = new ModelWrapper<>();
	
	private Validator firstnameValidator;
	private Validator lastnameValidator;
	private final Validator emailValidator = new EmailValidator(emailProperty());
	private final Validator birthdayValidator = new BirthdayValidator(birthdayProperty());
	
	private final Validator phoneValidator = new PhoneValidator(phoneNumberProperty(), "The phone number is invalid!");
	private final Validator mobileValidator = new PhoneValidator(mobileNumberProperty(),
			"The mobile number is invalid!");
			
	private final CompositeValidator formValidator = new CompositeValidator();
	
	@InjectScope
	ContactDialogScope dialogScope;
	
	public ContactFormViewModel() {
		firstnameValidator = new FunctionBasedValidator<>(
				firstnameProperty(),
				firstName -> firstName != null && !firstName.trim().isEmpty(),
				ValidationMessage.error("Firstname may not be empty"));
				
				
		lastnameValidator = new FunctionBasedValidator<>(lastnameProperty(), lastName -> {
			if (lastName == null || lastName.isEmpty()) {
				return ValidationMessage.error("Lastname may not be empty");
			} else if (lastName.trim().isEmpty()) {
				return ValidationMessage.error("Lastname may not only contain whitespaces");
			}
			
			return null;
		});
		
		
		formValidator.addValidators(
				firstnameValidator,
				lastnameValidator,
				emailValidator,
				birthdayValidator,
				phoneValidator,
				mobileValidator);
	}

    public void initialize() {
        dialogScope.subscribe(ContactDialogScope.Notifications.RESET_FORMS.toString(), (key, payload) -> resetForm());
        dialogScope.subscribe(ContactDialogScope.Notifications.COMMIT.toString(), (key, payload) -> commitChanges());

        dialogScope.contactToEditProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                initWithContact(newValue);
            }
        });


        dialogScope.contactFormValidProperty().bind(formValidator.getValidationStatus().validProperty());
    }


	private void resetForm() {
		contactWrapper.reset();
    }
	
	private void initWithContact(Contact contact) {
		this.contactWrapper.set(contact);
		this.contactWrapper.reload();
	}

    private void commitChanges() {
        if (contactWrapper.get() == null) {
            contactWrapper.set(new Contact());
        }

        contactWrapper.commit();
    }

	public ValidationStatus firstnameValidation() {
		return firstnameValidator.getValidationStatus();
	}
	
	public ValidationStatus lastnameValidation() {
		return lastnameValidator.getValidationStatus();
	}
	
	public ValidationStatus birthdayValidation() {
		return birthdayValidator.getValidationStatus();
	}
	
	public ValidationStatus emailValidation() {
		return emailValidator.getValidationStatus();
	}
	
	public ValidationStatus phoneValidation() {
		return phoneValidator.getValidationStatus();
	}
	
	public ValidationStatus mobileValidation() {
		return mobileValidator.getValidationStatus();
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
