package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

import java.util.function.Function;


public class Validator<T> {
	
	public static void connectToView(Control control, ObservableValue<ValidationResult> validationResultObservable) {
		
	}
	
	
	private ReadOnlyBooleanWrapper validationResult = new ReadOnlyBooleanWrapper();
	
	
	public Validator(ObservableValue<T> source, Function<T, ValidationResult> validatorFunction) {
		
	}
	
	public ObservableValue<ValidationResult> validationResultProperty(){
		return null;
	}

	
	public final ReadOnlyBooleanProperty validProperty() {
		return null;
	}
	public final ReadOnlyStringProperty errorMessageProperty() {
		return null;
	}
}
