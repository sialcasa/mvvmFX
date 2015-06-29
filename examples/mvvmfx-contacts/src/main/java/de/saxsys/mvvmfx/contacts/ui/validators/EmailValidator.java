package de.saxsys.mvvmfx.contacts.ui.validators;

import de.saxsys.mvvmfx.utils.validation.ObservableRules;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ObservableRuleBasedValidator;
import javafx.beans.value.ObservableValue;

import java.util.regex.Pattern;

/**
 * @author manuel.mauky
 */
public class EmailValidator extends ObservableRuleBasedValidator {
	private static final Pattern SIMPLE_EMAIL_PATTERN = Pattern
			.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	
	public EmailValidator(ObservableValue<String> source) {
		addRule(ObservableRules.notEmpty(source), ValidationMessage.error("Email may not be empty"));
		addRule(ObservableRules.matches(source, SIMPLE_EMAIL_PATTERN),
				ValidationMessage.warning("Maybe a wrong email format"));
	}
}
