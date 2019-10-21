package de.saxsys.mvvmfx.utils.validation.cssvisualizer;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.validation.FunctionBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import de.saxsys.mvvmfx.utils.validation.Validator;

import java.util.function.Function;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CssVisualizerViewModel implements ViewModel {
	private static final Pattern EMAIL_REGEX = Pattern
			.compile("^$|[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

	private StringProperty emailAddress = new SimpleStringProperty("");

	private Validator validator = new FunctionBasedValidator<String>(emailAddress, input -> {
		if (input == null || input.trim().isEmpty() || !EMAIL_REGEX.matcher(input).matches()) {
			return ValidationMessage.error("Invalid EMail address");
		} else {
			return null;
		}
	});

	public ValidationStatus getValidationStatus() {
		return validator.getValidationStatus();
	}

	public StringProperty emailAddressProperty() {
		return emailAddress;
	}
}
