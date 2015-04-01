package de.saxsys.mvvmfx.contacts.ui.master;

import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.util.CentralClock;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MasterTableViewModel {
	
	private ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();
	private StringProperty firstname = new SimpleStringProperty();
	private StringProperty lastname = new SimpleStringProperty();
	private StringProperty title = new SimpleStringProperty();
	
	private StringProperty emailAddress = new SimpleStringProperty();
	private IntegerProperty age = new SimpleIntegerProperty();
	
	private StringProperty city = new SimpleStringProperty();
	private StringProperty street = new SimpleStringProperty();
	private StringProperty postalCode = new SimpleStringProperty();
	
	public MasterTableViewModel(Contact contact) {
		id.set(contact.getId());
		setFirstname(contact.getFirstname());
		setLastname(contact.getLastname());
		setTitle(contact.getTitle());
		setEmailAddress(contact.getEmailAddress());
		setCity(contact.getAddress().getCity());
		setStreet(contact.getAddress().getStreet());
		setPostalCode(contact.getAddress().getPostalcode());
		
		if (contact.getBirthday() != null) {
			setAge((int) ChronoUnit.YEARS.between(contact.getBirthday(), LocalDate.now(CentralClock.getClock())));
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof MasterTableViewModel)) {
			return false;
		}
		
		MasterTableViewModel other = (MasterTableViewModel) obj;
		
		return other.getId().equals(this.getId());
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
	
	public String getId() {
		return id.get();
	}
	
	public ReadOnlyStringProperty idProperty() {
		return id.getReadOnlyProperty();
	}
	
	public String getFirstname() {
		return firstname.get();
	}
	
	public StringProperty firstnameProperty() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname.set(firstname);
	}
	
	public String getLastname() {
		return lastname.get();
	}
	
	public StringProperty lastnameProperty() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname.set(lastname);
	}
	
	public String getTitle() {
		return title.get();
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title.set(title);
	}
	
	public String getEmailAddress() {
		return emailAddress.get();
	}
	
	public StringProperty emailAddressProperty() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress.set(emailAddress);
	}
	
	public int getAge() {
		return age.get();
	}
	
	public IntegerProperty ageProperty() {
		return age;
	}
	
	public void setAge(int age) {
		this.age.set(age);
	}
	
	public String getCity() {
		return city.get();
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
	public void setCity(String city) {
		this.city.set(city);
	}
	
	public String getStreet() {
		return street.get();
	}
	
	public StringProperty streetProperty() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street.set(street);
	}
	
	public String getPostalCode() {
		return postalCode.get();
	}
	
	public StringProperty postalCodeProperty() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode.set(postalCode);
	}
}
