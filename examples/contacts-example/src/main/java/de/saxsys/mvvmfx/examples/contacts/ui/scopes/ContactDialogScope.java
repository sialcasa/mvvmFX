package de.saxsys.mvvmfx.examples.contacts.ui.scopes;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContactDialogScope implements Scope {
	
	public static String RESET_DIALOG_PAGE = "contact_reset_dialog_page";
	public static String OK_BEFORE_COMMIT = "contact_ok_before_commit";
	public static String COMMIT = "contact_commit";
	public static String RESET_FORMS = "contact_reset";
	
	private final ObjectProperty<Contact> contactToEdit = new SimpleObjectProperty<>(this, "contactToEdit");
	
	private final BooleanProperty contactFormValid = new SimpleBooleanProperty();
	private final BooleanProperty addressFormValid = new SimpleBooleanProperty();
	private final BooleanProperty bothFormsValid = new SimpleBooleanProperty();
	private final StringProperty dialogTitle = new SimpleStringProperty();
	
	
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
	
	public final BooleanProperty bothFormsValidProperty() {
		return this.bothFormsValid;
	}
	
	
	public final boolean isBothFormsValid() {
		return this.bothFormsValidProperty().get();
	}
	
	
	public final void setBothFormsValid(final boolean bothFormsValid) {
		this.bothFormsValidProperty().set(bothFormsValid);
	}
	
	public final StringProperty dialogTitleProperty() {
		return this.dialogTitle;
	}
	
	
	public final java.lang.String getDialogTitle() {
		return this.dialogTitleProperty().get();
	}
	
	
	public final void setDialogTitle(final java.lang.String dialogTitle) {
		this.dialogTitleProperty().set(dialogTitle);
	}
	
	
	
	
}
