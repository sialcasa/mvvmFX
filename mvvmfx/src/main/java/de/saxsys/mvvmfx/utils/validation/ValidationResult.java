package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Control;


/**
 * @author manuel.mauky
 */
public class ValidationResult {
	
	private ListProperty<ValidationMessage> messages = new SimpleListProperty<>(FXCollections.observableArrayList());
	private FilteredList<ValidationMessage> errorMessages = new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.ERROR));
	private FilteredList<ValidationMessage> warningMessages = new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.WARNING));
	private FilteredList<ValidationMessage> infoMessages = new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.INFO));
	
	private ValidationResult(){
	}
	
	public ObservableList<ValidationMessage> getMessages() {
		return messages;
	}

	public ObservableList<ValidationMessage> getErrorMessages() {
		return errorMessages;
	}

	public ObservableList<ValidationMessage> getWarningMessages() {
		return warningMessages;	
	}
	
	public ObservableList<ValidationMessage> getInfoMessages() {
		return infoMessages;
	}
	
	/**
	 * @return <code>true</code> if there are no validation messages present.
	 */
	public ReadOnlyBooleanProperty validProperty() {
		return messages.emptyProperty();
	}
	
}
