package de.saxsys.mvvmfx.contacts.ui.addcontact;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

import javax.inject.Inject;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.model.validation.BirthdayValidator;
import de.saxsys.mvvmfx.contacts.model.validation.EmailAddressValidator;
import de.saxsys.mvvmfx.contacts.model.validation.PhoneNumberValidator;

public class AddContactPopupViewModel implements ViewModel {
	private StringProperty firstname = new SimpleStringProperty();
	private StringProperty title = new SimpleStringProperty();
	private StringProperty lastname = new SimpleStringProperty();
	
	private StringProperty role = new SimpleStringProperty();
	private StringProperty department = new SimpleStringProperty();
	
	private ObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>();
	
	private StringProperty email = new SimpleStringProperty();
	private StringProperty mobileNumber = new SimpleStringProperty();
	private StringProperty phoneNumber = new SimpleStringProperty();
	
	private BooleanProperty popupOpen = new SimpleBooleanProperty();
	
	private ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper();
	
	private ReadOnlyBooleanWrapper addButtonDisabled = new ReadOnlyBooleanWrapper();
	
	@Inject
	private Repository repository;
	
	ValidationSupport validationSupport = new ValidationSupport();
	
	public AddContactPopupViewModel() {
		valid.bind(validationSupport.invalidProperty().isNull().or(validationSupport.invalidProperty().isEqualTo
				(false)));
		
		addButtonDisabled.bind(valid.not());
	}
	
	public void resetForm() {
		firstname.set("");
		lastname.set("");
		title.set("");
		role.set("");
		department.set("");
		email.set("");
		mobileNumber.set("");
		phoneNumber.set("");
		
		birthday.set(null);
	}
	
	public void addContactAction() {
		if (valid.get()) {
			// Add logic to persist the new contact.
			
			Contact contact = new Contact();
			contact.setFirstname(firstname.get());
			contact.setLastname(lastname.get());
			contact.setTitle(title.get());
			
			contact.setRole(role.get());
			contact.setDepartment(department.get());
			
			contact.setBirthday(birthday.get());
			contact.setEmailAddress(email.get());
			contact.setMobileNumber(mobileNumber.get());
			contact.setPhoneNumber(phoneNumber.get());
			
			repository.save(contact);
			
			popupOpen.set(false);
		}
	}
	
	public void openDialog() {
		this.resetForm();
		this.popupOpenProperty().set(true);
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
	
	public BooleanProperty popupOpenProperty() {
		return popupOpen;
	}
	
	
	public ReadOnlyBooleanProperty addButtonDisabledProperty() {
		return addButtonDisabled.getReadOnlyProperty();
	}
	
	public StringProperty firstnameProperty() {
		return firstname;
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public StringProperty lastnameProperty() {
		return lastname;
	}
	
	public StringProperty roleProperty() {
		return role;
	}
	
	public StringProperty departmentProperty() {
		return department;
	}
	
	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}
	
	public StringProperty emailProperty() {
		return email;
	}
	
	public StringProperty mobileNumberProperty() {
		return mobileNumber;
	}
	
	public StringProperty phoneNumberProperty() {
		return phoneNumber;
	}
	
	
}
