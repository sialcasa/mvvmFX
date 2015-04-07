package de.saxsys.mvvmfx.contacts.ui.contactform;

import de.saxsys.mvvmfx.utils.validation.ValidationResult;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author manuel.mauky
 */
public class EmailValidator implements Function<String, ValidationResult> {
	private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern
			.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	
	@Override
	public ValidationResult apply(String input) {
		if (input == null || input.trim().isEmpty()) {
			return ValidationResult.error( "The email address may not be empty!");
		}

		if (!SIMPLE_EMAIL_PATTERN.matcher(input).matches()) {
			return ValidationResult.error( "The email address is invalid!");
		}

		return ValidationResult.ok();
	}
}
