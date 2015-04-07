package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableBooleanValue;

public abstract class Validator {
	// Validatorchain
	
	private final ReadOnlyBooleanWrapper validProperty = new ReadOnlyBooleanWrapper();
	
	private final ObservableBooleanValue validBinding;
	
	
	public Validator(ObservableBooleanValue validBinding) {
		this.validBinding = validBinding;
		validProperty.bind(validBinding);
	}
	
	public final ReadOnlyBooleanProperty validProperty() {
		return validProperty.getReadOnlyProperty();
	}
	
	public final ReadOnlyStringProperty errorMessageProperty() {
		return null;
	}
}
