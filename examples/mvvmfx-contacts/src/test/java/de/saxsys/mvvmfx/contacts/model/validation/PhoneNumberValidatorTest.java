package de.saxsys.mvvmfx.contacts.model.validation;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class PhoneNumberValidatorTest {
	
	private PhoneNumberValidator validator;
	
	@Before
	public void setup() {
		validator = new PhoneNumberValidator("Error Message");
	}
	
	@Test
	public void testPhoneNumber() {
		TextField phoneNumberInput = new TextField();
		
		assertThat(validator.apply(phoneNumberInput, "")).isNull();
		ValidationResult apply = validator.apply(phoneNumberInput, "012345678");
		
		assertThat(validator.apply(phoneNumberInput, "012345678")).isNull();
		assertThat(validator.apply(phoneNumberInput, "+49 1234 324541")).isNull();
		
		assertThat(validator.apply(phoneNumberInput, null)).isNull(); // empty phonenumber is ok
		assertThat(validator.apply(phoneNumberInput, "")).isNull();
		assertThat(validator.apply(phoneNumberInput, "  ")).isNull();
		
		assertThat(validator.apply(phoneNumberInput, "abc")).isNotNull();
		
		
	}
}
