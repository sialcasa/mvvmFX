package de.saxsys.mvvmfx.examples.contacts.ui.scopes;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ContactDialogScope implements Scope {
	
	public enum Notifications {
		RESET_DIALOG_PAGE, COMMIT, RESET_FORMS;
	}
	
	
	private final BooleanProperty contactFormValid = new SimpleBooleanProperty();
	private final BooleanProperty addressFormValid = new SimpleBooleanProperty();
	private final ObjectProperty<Contact> contactToEdit = new SimpleObjectProperty<>(this, "contactToEdit");
	
	public BooleanProperty contactFormValidProperty() {
		return this.contactFormValid;
	}
	
	public boolean isContactFormValid() {
		return this.contactFormValidProperty().get();
	}
	
	public void setContactFormValid(final boolean contactFormValid) {
		this.contactFormValidProperty().set(contactFormValid);
	}
	
	public BooleanProperty addressFormValidProperty() {
		return this.addressFormValid;
	}
	
	public boolean isAddressFormValid() {
		return this.addressFormValidProperty().get();
	}
	
	public void setAddressFormValid(final boolean addressFormValid) {
		this.addressFormValidProperty().set(addressFormValid);
	}
	
	public ObjectProperty<Contact> contactToEditProperty() {
		return this.contactToEdit;
	}
	
	
	public Contact getContactToEdit() {
		return this.contactToEditProperty().get();
	}
	
	
	public void setContactToEdit(final Contact contactToEdit) {
		this.contactToEditProperty().set(contactToEdit);
	}
	
	
}
