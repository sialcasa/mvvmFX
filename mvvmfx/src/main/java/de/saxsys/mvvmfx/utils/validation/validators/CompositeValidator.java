package de.saxsys.mvvmfx.utils.validation.validators;

import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import de.saxsys.mvvmfx.utils.validation.Validator;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This {@link Validator} implementation is used to compose multiple other validators.
 *
 * The {@link ValidationStatus} of this validator will contain all messages of all registered
 * validators.
 *
 *
 * @author manuel.mauky
 */
public class CompositeValidator implements Validator {
	
	private CompositeValidationResult result = new CompositeValidationResult();
	
	public CompositeValidator() {
	}
	
	public CompositeValidator(Validator... validators) {
		registerValidator(validators);
	}
	
	
	public void registerValidator(Validator... validators) {
		result.addResults(Stream.of(validators)
				.map(Validator::getValidationStatus)
				.collect(Collectors.toList()));
	}
	
	public void unregisterValidator(Validator... validators) {
		result.removeResults(Stream.of(validators)
				.map(Validator::getValidationStatus)
				.collect(Collectors.toList()));
	}

	@Override
	public ValidationStatus getValidationStatus() {
		return result;
	}
}
