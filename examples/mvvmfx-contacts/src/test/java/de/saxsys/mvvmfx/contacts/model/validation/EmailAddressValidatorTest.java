package de.saxsys.mvvmfx.contacts.model.validation;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class EmailAddressValidatorTest {
	
	private EmailAddressValidator validator;
	
	@Before
	public void setup() {
		validator = new EmailAddressValidator();
	}
	
	@Test
	public void testValidationOfEmail() {
		TextField emailInput = new TextField();
		
		assertThat(validator.apply(emailInput, "darthvader@imperium.org")).isNull();
		
		assertThat(validator.apply(emailInput, "darthvader.imperium.org")).isNotNull(); // wrong email format
		
		assertThat(validator.apply(emailInput, null)).isNotNull();
		assertThat(validator.apply(emailInput, "")).isNotNull();
		assertThat(validator.apply(emailInput, "   ")).isNotNull();
	}
}
