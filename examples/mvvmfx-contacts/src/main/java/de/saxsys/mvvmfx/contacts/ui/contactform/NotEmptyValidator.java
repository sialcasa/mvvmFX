package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.utils.validation.ValidationResult;

import java.util.function.Function;

/**
 * @author manuel.mauky
 */
public class NotEmptyValidator implements Function<String, ValidationResult> {

	private String errorMessage;
	
	public NotEmptyValidator() {
		errorMessage = "The value may not be empty!";
	}
	
	public NotEmptyValidator(String errorMessage){
		this.errorMessage = errorMessage;
	}


	@Override
	public ValidationResult apply(String input) {
		if(input == null ||input.trim().isEmpty()) {
			return ValidationResult.error(errorMessage);
		} else {
			return ValidationResult.ok();
		}
	}
}
