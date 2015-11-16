package de.saxsys.mvvmfx.examples.contacts.ui.scopes;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MasterDetailScope implements Scope {
	
	private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>(this, "selectedContact");
	
	public ObjectProperty<Contact> selectedContactProperty() {
		return this.selectedContact;
	}
	
	
	public final Contact getSelectedContact() {
		return this.selectedContactProperty().get();
	}
	
	
	public final void setSelectedContact(final Contact selectedContact) {
		this.selectedContactProperty().set(selectedContact);
	}
	
	
}
