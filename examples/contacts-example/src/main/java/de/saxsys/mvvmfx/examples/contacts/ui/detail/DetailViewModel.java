package de.saxsys.mvvmfx.examples.contacts.ui.detail;

import static eu.lestard.advanced_bindings.api.ObjectBindings.map;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Address;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.MasterDetailScope;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.application.HostServices;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class DetailViewModel implements ViewModel {
	
	public static final String OPEN_EDIT_CONTACT_DIALOG = "open_edit_contact";
	
	private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ISO_DATE;
	
	private final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
	
	private final ReadOnlyStringWrapper birthday = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper roleDepartment = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper email = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper phone = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper mobile = new ReadOnlyStringWrapper();
	
	private final ReadOnlyStringWrapper cityPostalcode = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper street = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper countrySubdivision = new ReadOnlyStringWrapper();
	
	
	private DelegateCommand editCommand;
	private DelegateCommand removeCommand;
	private DelegateCommand emailLinkCommand;
	
	@Inject
	HostServices hostServices;
	
	@Inject
	Repository repository;
	
	@InjectScope
	MasterDetailScope mdScope;
	
	@InjectScope
	ContactDialogScope dialogscope;
	
	public void initialize() {
		ReadOnlyObjectProperty<Contact> contactProperty = getSelectedContactPropertyFromScope();
		
		createBindingsForLabels(contactProperty);
		
		editCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Contact selectedContact = getSelectedContactFromScope();
				if (selectedContact != null) {
					dialogscope.setContactToEdit(selectedContact);
					publish(OPEN_EDIT_CONTACT_DIALOG);
				}
			}
		}, getSelectedContactPropertyFromScope().isNotNull());
		
		removeCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Contact selectedContact = getSelectedContactFromScope();
				if (selectedContact != null) {
					repository.delete(getSelectedContactFromScope());
				}
			}
			
		}, getSelectedContactPropertyFromScope().isNotNull());
		
		emailLinkCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				if (email.get() != null && !email.get().trim().isEmpty()) {
					hostServices.showDocument("mailto:" + email.get());
				}
			}
		});
	}
	
	private void createBindingsForLabels(ReadOnlyObjectProperty<Contact> contactProperty) {
		name.bind(emptyStringOnNull(map(contactProperty, contact -> {
			StringBuilder result = new StringBuilder();
			
			String title = contact.getTitle();
			if (title != null && !title.trim().isEmpty()) {
				result.append(title);
				result.append(" ");
			}
			
			result.append(contact.getFirstname());
			result.append(" ");
			result.append(contact.getLastname());
			
			return result.toString();
		})));
		
		
		email.bind(emptyStringOnNull(map(contactProperty, Contact::getEmailAddress)));
		
		roleDepartment.bind(emptyStringOnNull(map(contactProperty, contact -> {
			StringBuilder result = new StringBuilder();
			if (contact.getRole() != null && !contact.getRole().trim().isEmpty()) {
				result.append(contact.getRole());
				
				if (contact.getDepartment() != null && !contact.getDepartment().trim().isEmpty()) {
					result.append(" / ");
					result.append(contact.getDepartment());
				}
			} else if (contact.getDepartment() != null) {
				result.append(contact.getDepartment());
			}
			
			return result.toString();
		})));
		
		birthday.bind(emptyStringOnNull(map(contactProperty, contact -> {
			LocalDate date = contact.getBirthday();
			if (date == null) {
				return "";
			} else {
				return BIRTHDAY_FORMATTER.format(date);
			}
		})));
		
		phone.bind(emptyStringOnNull(map(contactProperty, Contact::getPhoneNumber)));
		
		mobile.bind(emptyStringOnNull(map(contactProperty, Contact::getMobileNumber)));
		
		ObjectBinding<Address> addressBinding = map(contactProperty, Contact::getAddress);
		
		cityPostalcode.bind(emptyStringOnNull(map(addressBinding, address -> {
			StringBuilder result = new StringBuilder();
			if (address.getCity() != null) {
				result.append(address.getCity());
			}
			
			if (address.getPostalcode() != null) {
				result.append(" (");
				result.append(address.getPostalcode());
				result.append(")");
			}
			return result.toString();
		})));
		
		street.bind(emptyStringOnNull(map(addressBinding, Address::getStreet)));
		
		countrySubdivision.bind(emptyStringOnNull(map(addressBinding, address -> {
			StringBuilder result = new StringBuilder();
			if (address.getCountry() != null) {
				result.append(address.getCountry().getName());
			}
			
			if (address.getSubdivision() != null) {
				result.append(" / ");
				result.append(address.getSubdivision().getName());
			}
			return result.toString();
		})));
	}
	
	/**
	 * When the given source binding has a value of <code>null</code> an empty string is used for the returned binding.
	 * Otherwise the value of the source binding is used.
	 */
	private StringBinding emptyStringOnNull(ObjectBinding<String> source) {
		return Bindings.createStringBinding(() -> {
			if (source.get() == null) {
				return "";
			} else {
				return source.get();
			}
		} , source);
	}
	
	public Command getEditCommand() {
		return editCommand;
	}
	
	public Command getRemoveCommand() {
		return removeCommand;
	}
	
	public Command getEmailLinkCommand() {
		return emailLinkCommand;
	}
	
	public ReadOnlyStringProperty nameLabelTextProperty() {
		return name.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty birthdayLabelTextProperty() {
		return birthday.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty roleDepartmentLabelTextProperty() {
		return roleDepartment.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty emailLabelTextProperty() {
		return email.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty phoneLabelTextProperty() {
		return phone.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty mobileLabelTextProperty() {
		return mobile.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty cityPostalcodeLabelTextProperty() {
		return cityPostalcode.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty streetLabelTextProperty() {
		return street.getReadOnlyProperty();
	}
	
	public ReadOnlyStringProperty countrySubdivisionLabelTextProperty() {
		return countrySubdivision.getReadOnlyProperty();
	}
	
	private String trimString(String string) {
		if (string == null || string.trim().isEmpty()) {
			return "";
		}
		return string;
	}
	
	private String trimStringWithPostfix(String string, String append) {
		if (string == null || string.trim().isEmpty()) {
			return "";
		}
		return string + append;
	}
	
	private Contact getSelectedContactFromScope() {
		return getSelectedContactPropertyFromScope().get();
	}
	
	private ObjectProperty<Contact> getSelectedContactPropertyFromScope() {
		return mdScope.selectedContactProperty();
	}
}
