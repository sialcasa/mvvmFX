package de.saxsys.mvvmfx.utils.validation;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * @author manuel.mauky
 */
public class CompositeValidator implements Validator {
	
	private ListProperty<ValidationResult> results = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	private CompositeValidationResult result = new CompositeValidationResult();
	
	public CompositeValidator() {
	}
	
	public CompositeValidator(Validator... validators) {
		registerValidator(validators);
	}
	
	
	public void registerValidator(Validator... validators) {
		result.addResults(Stream.of(validators)
				.map(Validator::getResult)
				.collect(Collectors.toList()));
	}
	
	public void unregisterValidator(Validator... validators) {
		result.removeResults(Stream.of(validators)
				.map(Validator::getResult)
				.collect(Collectors.toList()));
	}
	
	
	
	
	@Override
	public ValidationResult getResult() {
		return result;
	}
}
