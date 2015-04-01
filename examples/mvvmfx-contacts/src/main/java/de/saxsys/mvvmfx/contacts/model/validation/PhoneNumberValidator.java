package de.saxsys.mvvmfx.contacts.model.validation;

import java.util.regex.Pattern;

import javafx.scene.control.Control;

import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 * A Validator that verifies that a given String is a valid phone number.
 */
public class PhoneNumberValidator implements Validator<String> {
	private static final Pattern SIMPLE_PHONE_PATTERN = Pattern.compile("\\+?[0-9\\s]{3,20}");
	private String message;
	
	public PhoneNumberValidator(String message) {
		this.message = message;
	}
	
	@Override
	public ValidationResult apply(Control control, String input) {
		if (input == null || input.trim().isEmpty()) {
			return null;
		}
		
		if (!SIMPLE_PHONE_PATTERN.matcher(input).matches()) {
			return ValidationResult.fromError(control, message);
		}
		
		return null;
	}
}
