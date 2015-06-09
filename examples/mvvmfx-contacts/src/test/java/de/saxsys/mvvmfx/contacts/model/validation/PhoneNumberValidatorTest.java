package de.saxsys.mvvmfx.contacts.model.validation;

import static org.assertj.core.api.Assertions.*;

import de.saxsys.mvvmfx.utils.validation.Validator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.utils.validation.ValidationResult;

public class PhoneNumberValidatorTest {
	
	private ValidationResult result;
	private StringProperty value = new SimpleStringProperty();
	
	
	@Before
	public void setup() {
		Validator validator = new PhoneValidator(value, "error message");
		
		result = validator.getResult();
	}
	
	
	@Test
	public void testPhoneNumber() {
		// phone number is not mandatory
		value.set("");
		assertThat(result.isValid()).isTrue(); 
		value.set(null);
		assertThat(result.isValid()).isTrue(); 
		value.set("   ");
		assertThat(result.isValid()).isTrue(); 


		value.set("012345678");
		assertThat(result.isValid()).isTrue();
		
		value.set("+49 1234 324541");
		assertThat(result.isValid()).isTrue();
		
		value.set("abc");
		assertThat(result.isValid()).isFalse();
	}
}
