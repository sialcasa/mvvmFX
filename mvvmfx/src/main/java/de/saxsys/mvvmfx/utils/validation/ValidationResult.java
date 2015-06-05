package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * @author manuel.mauky
 */
public class ValidationResult {
	
	private ListProperty<ValidationMessage> messages = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObservableList<ValidationMessage> unmodifiableMessages = FXCollections.unmodifiableObservableList(messages);
	private ObservableList<ValidationMessage> errorMessages = FXCollections.unmodifiableObservableList(new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.ERROR)));
	private ObservableList<ValidationMessage> warningMessages = FXCollections.unmodifiableObservableList(new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.WARNING)));
	
	
    void addMessage(ValidationMessage message) {
        messages.add(message);
	}
	
	public ObservableList<ValidationMessage> getMessages() {
		return unmodifiableMessages;
	}

	public ObservableList<ValidationMessage> getErrorMessages() {
		return errorMessages;
	}

	public ObservableList<ValidationMessage> getWarningMessages() {
		return warningMessages;
	}

	/**
	 * @return <code>true</code> if there are no validation messages present.
	 */
	public ReadOnlyBooleanProperty validProperty() {
		return messages.emptyProperty();
	}
	
	public boolean isValid() {
		return validProperty().get();
	}


    void removeMessage(ValidationMessage message) {
        messages.removeAll(message);
    }
}
