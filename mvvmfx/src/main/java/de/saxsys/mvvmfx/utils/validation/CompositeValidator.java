package de.saxsys.mvvmfx.utils.validation;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This {@link Validator} implementation is used to compose multiple other validators.
 * <p>
 * The {@link ValidationStatus} of this validator will contain all messages of all registered validators.
 *
 *
 * @author manuel.mauky
 */
public class CompositeValidator implements Validator {
	
	private CompositeValidationResult result = new CompositeValidationResult();
	
	public CompositeValidator() {
	}
	
	public CompositeValidator(Validator... validators) {
		addValidators(validators);
	}
	
	
	public void addValidators(Validator... validators) {
		result.addResults(Stream.of(validators)
				.map(Validator::getValidationStatus)
				.collect(Collectors.toList()));
	}
	
	public void removeValidators(Validator... validators) {
		result.removeResults(Stream.of(validators)
				.map(Validator::getValidationStatus)
				.collect(Collectors.toList()));
	}
	
	@Override
	public ValidationStatus getValidationStatus() {
		return result;
	}
}
