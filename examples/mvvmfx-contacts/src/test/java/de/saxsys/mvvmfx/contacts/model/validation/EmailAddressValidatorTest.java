package de.saxsys.mvvmfx.contacts.model.validation;

import static org.assertj.core.api.Assertions.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.utils.validation.ValidationResult;
import de.saxsys.mvvmfx.utils.validation.Validator;

public class EmailAddressValidatorTest {
	
	private ValidationResult result;
	private StringProperty value = new SimpleStringProperty();
	
	
	@Before
	public void setup() {
		Validator validator = new EmailValidator(value);
		
		result = validator.getValidationResult();
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
