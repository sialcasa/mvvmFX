package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;

import java.util.function.Function;


public class Validator<T> {
	
	public Validator(ObservableValue<T> source) {
		
	}
	
	public void addRule(Function<T, ValidationMessage> rule){
		
	}
	
	
	public ValidationResult getValidationResult(){
		return null;
	}
	
}
