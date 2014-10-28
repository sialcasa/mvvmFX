package de.saxsys.mvvmfx.contacts.ui.contactform;

import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Control;

import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.validation.BirthdayValidator;
import de.saxsys.mvvmfx.contacts.model.validation.EmailAddressValidator;
import de.saxsys.mvvmfx.contacts.model.validation.PhoneNumberValidator;

public class ContactFormViewModel implements ViewModel {
	
	private StringProperty firstname = new SimpleStringProperty();
	private StringProperty title = new SimpleStringProperty();
	private StringProperty lastname = new SimpleStringProperty();

	private StringProperty role = new SimpleStringProperty();
	private StringProperty department = new SimpleStringProperty();

	private ObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>();

	private StringProperty email = new SimpleStringProperty();
	private StringProperty mobileNumber = new SimpleStringProperty();
	private StringProperty phoneNumber = new SimpleStringProperty();



	private ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper();


	ValidationSupport validationSupport = new ValidationSupport();
	private Contact contact;
	
	public ContactFormViewModel(){
		valid.bind(validationSupport.invalidProperty().isNull().or(validationSupport.invalidProperty().isEqualTo
				(false)));
		
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
	
	public void initWithContact(Contact contact){
		// init the values with the contact.
		this.contact = contact;
		
		firstname.set(contact.getFirstname());
		lastname.set(contact.getLastname());
		title.set(contact.getTitle());
		
		role.set(contact.getRole());
		department.set(contact.getDepartment());
		
		birthday.set(contact.getBirthday());
		email.set(contact.getEmailAddress());
		mobileNumber.set(contact.getMobileNumber());
		phoneNumber.set(contact.getPhoneNumber());
	}
	
	public Contact getContact(){
		// use existing contact if it was set, otherwise create new instance
		Contact resultContact = (contact == null) ? new Contact() : contact;

		resultContact.setFirstname(firstname.get());
		resultContact.setLastname(lastname.get());
		resultContact.setTitle(title.get());

		resultContact.setRole(role.get());
		resultContact.setDepartment(department.get());

		resultContact.setBirthday(birthday.get());
		resultContact.setEmailAddress(email.get());
		resultContact.setMobileNumber(mobileNumber.get());
		resultContact.setPhoneNumber(phoneNumber.get());

		return resultContact;
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
