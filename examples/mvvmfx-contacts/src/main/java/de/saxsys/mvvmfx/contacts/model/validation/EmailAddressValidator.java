package de.saxsys.mvvmfx.contacts.model.validation;

import java.util.regex.Pattern;

import javafx.scene.control.Control;

import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/**
 * A validator that verifies a given String to be a valid email address.
 */
public class EmailAddressValidator implements Validator<String> {
	
	private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern
			.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	
	@Override
	public ValidationResult apply(Control control, String newValue) {
		if (newValue == null || newValue.trim().isEmpty()) {
			return ValidationResult.fromError(control, "The email address may not be empty!");
		}
		
		if (!SIMPLE_EMAIL_PATTERN.matcher(newValue).matches()) {
			return ValidationResult.fromError(control, "The email address is invalid!");
		}
		
		return null;
	}
}
