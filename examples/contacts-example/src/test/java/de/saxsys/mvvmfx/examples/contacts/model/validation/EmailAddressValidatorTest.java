package de.saxsys.mvvmfx.examples.contacts.model.validation;

import static org.assertj.core.api.Assertions.*;

import de.saxsys.mvvmfx.examples.contacts.ui.validators.EmailValidator;
import de.saxsys.mvvmfx.utils.validation.Validator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.utils.validation.ValidationStatus;

public class EmailAddressValidatorTest {

	private ValidationStatus result;
	private StringProperty value = new SimpleStringProperty();

	@Before
	public void setup() {
		Validator validator = new EmailValidator(value);

		result = validator.getValidationStatus();
	}

	@Test
	public void testValidationOfEmail() {
		assertThat(result.isValid()).isFalse();

		value.set("darthvader@imperium.org");
		assertThat(result.isValid()).isTrue();

		value.set("darthvader.imperium.org"); // wrong email format
		assertThat(result.isValid()).isFalse();

		value.set(null);
		assertThat(result.isValid()).isFalse();

		value.set("");
		assertThat(result.isValid()).isFalse();

		value.set("    ");
		assertThat(result.isValid()).isFalse();
	}

}
