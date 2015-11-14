package de.saxsys.mvvmfx.examples.contacts.ui.master;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.util.CentralClock;
import de.saxsys.mvvmfx.utils.mapping.ModelWrapper;
import de.saxsys.mvvmfx.utils.mapping.accessorfunctions.StringGetter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class MasterTableViewModel {
	
	private final String id;
	private final IntegerProperty age = new SimpleIntegerProperty();
	private final ModelWrapper<Contact> contactWrapper = new ModelWrapper<>();
	
	public MasterTableViewModel(Contact contact) {
		id = contact.getId();
		contactWrapper.set(contact);
		contactWrapper.reload();
		
		if (contact.getBirthday() != null) {
			age.set((int) ChronoUnit.YEARS.between(contact.getBirthday(), LocalDate.now(CentralClock.getClock())));
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
		return id;
	}
	
	public StringProperty firstnameProperty() {
		return contactWrapper.field("firstname", Contact::getFirstname, Contact::setFirstname);
	}
	
	
	public StringProperty lastnameProperty() {
		return contactWrapper.field("lastname", Contact::getLastname, Contact::setLastname);
	}
	
	
	public StringProperty titleProperty() {
		return contactWrapper.field("title", Contact::getTitle, Contact::setTitle);
	}
	
	public StringProperty emailAddressProperty() {
		return contactWrapper.field("emailAddress", Contact::getEmailAddress, Contact::setEmailAddress);
	}
	
	public IntegerProperty ageProperty() {
		return age;
	}
	
	public StringProperty cityProperty() {
		return contactWrapper.field("city",
				(StringGetter<Contact>) model -> model.getAddress().getCity(),
				(model, value) -> model.getAddress().setCity(value));
	}
	
	
	public StringProperty streetProperty() {
		return contactWrapper.field("street",
				(StringGetter<Contact>) model -> model.getAddress().getStreet(),
				(model, value) -> model.getAddress().setStreet(value));
	}
	
	
	public StringProperty postalCodeProperty() {
		return contactWrapper.field("postalcode",
				(StringGetter<Contact>) model -> model.getAddress().getPostalcode(),
				(model, value) -> model.getAddress().setPostalcode(value));
	}
}
