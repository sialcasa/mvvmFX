package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * @author manuel.mauky
 */
public class ValidationResult {
	
	private ListProperty<ValidationMessage> messages = new SimpleListProperty<>(FXCollections.observableArrayList());
	private FilteredList<ValidationMessage> errorMessages = new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.ERROR));
	private FilteredList<ValidationMessage> warningMessages = new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.WARNING));

	ValidationResult(){
	}

    void addMessage(ValidationMessage message) {
        messages.add(message);
    }
	
	public ObservableList<ValidationMessage> getMessages() {
		return FXCollections.unmodifiableObservableList(messages);
	}

	public ObservableList<ValidationMessage> getErrorMessages() {
		return FXCollections.unmodifiableObservableList(errorMessages);
	}

	public ObservableList<ValidationMessage> getWarningMessages() {
		return FXCollections.unmodifiableObservableList(warningMessages);
	}

	/**
	 * @return <code>true</code> if there are no validation messages present.
	 */
	public ReadOnlyBooleanProperty validProperty() {
		return messages.emptyProperty();
	}


    void removeMessage(ValidationMessage message) {
        messages.remove(message);
    }
}
