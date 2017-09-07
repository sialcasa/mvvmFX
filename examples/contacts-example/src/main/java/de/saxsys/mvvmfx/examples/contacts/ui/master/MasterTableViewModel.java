package de.saxsys.mvvmfx.examples.contacts.ui.master;

import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.util.CentralClock;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MasterTableViewModel {

	private Contact contact;

	public MasterTableViewModel(Contact contact) {
		this.contact = contact;
	}

	public String getId() {
		return contact.getId();
	}

	public String getFirstname() {
		return contact.getFirstname();
	}

	public String getLastname() {
		return contact.getLastname();
	}

	public Integer getAge(){
		if (contact.getBirthday() == null) {
			return null;
		} else {
			return (int) ChronoUnit.YEARS.between(contact.getBirthday(), LocalDate.now(CentralClock.getClock()));
		}
	}

	public String getTitle() {
		return contact.getTitle();
	}

	public String getEmailAddress() {
		return contact.getEmailAddress();
	}

	public String getCity() {
		return contact.getAddress().getCity();
	}

	public String getStreet() {
		return contact.getAddress().getStreet();
	}

	public String getPostalCode() {
		return contact.getAddress().getPostalcode();
	}

}
