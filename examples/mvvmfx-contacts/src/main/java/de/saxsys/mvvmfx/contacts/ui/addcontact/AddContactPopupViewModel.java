package de.saxsys.mvvmfx.contacts.ui.addcontact;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

import java.time.LocalDate;

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
	
	public void addContactAction(){
		// Add logic to persist the new contact.
		
		popupOpen.set(false);
	}
	
	public void openDialog(){
		this.resetForm();
		this.popupOpenProperty().set(true);
	}

	
	public BooleanProperty popupOpenProperty(){
		return popupOpen;
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
