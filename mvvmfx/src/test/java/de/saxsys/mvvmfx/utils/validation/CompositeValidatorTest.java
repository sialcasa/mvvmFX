package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author manuel.mauky
 */
public class CompositeValidatorTest {
	
	private ValidationResult result;
	
	private BooleanProperty valid1 = new SimpleBooleanProperty();
	private BooleanProperty valid2 = new SimpleBooleanProperty();
	private CompositeValidator compositeValidator;
	private	RuleBasedValidator validator1;
	private	RuleBasedValidator validator2;
	
	@Before
	public void setup() {
		validator1 = new RuleBasedValidator();
		validator1.addRule(valid1, ValidationMessage.error("error1"));
		
		validator2 = new RuleBasedValidator();
		validator2.addRule(valid2, ValidationMessage.warning("warning2"));
		
		
		compositeValidator = new CompositeValidator();
		result = compositeValidator.getResult();
	}
	
	@Test
	public void testValidation() {
		compositeValidator.registerValidator(validator1, validator2);
		
		valid1.set(true);
		valid2.set(true);
		
		assertThat(result.isValid()).isTrue();
		
		valid1.setValue(false);
		
		assertThat(result.isValid()).isFalse();
		assertThat(asStrings(result.getErrorMessages())).containsOnly("error1");
		assertThat(asStrings(result.getWarningMessages())).isEmpty();
		assertThat(asStrings(result.getMessages())).containsOnly("error1");
		
		valid2.setValue(false);
		assertThat(result.isValid()).isFalse();
		assertThat(asStrings(result.getErrorMessages())).containsOnly("error1");
		assertThat(asStrings(result.getWarningMessages())).containsOnly("warning2");
		assertThat(asStrings(result.getMessages())).containsOnly("error1", "warning2");
		
		valid1.setValue(true);
		assertThat(result.isValid()).isFalse();
		assertThat(asStrings(result.getErrorMessages())).isEmpty();
		assertThat(asStrings(result.getWarningMessages())).containsOnly("warning2");
		assertThat(asStrings(result.getMessages())).containsOnly("warning2");

		valid2.setValue(true);
		assertThat(result.isValid()).isTrue();
		assertThat(asStrings(result.getErrorMessages())).isEmpty();
		assertThat(asStrings(result.getWarningMessages())).isEmpty();
		assertThat(asStrings(result.getMessages())).isEmpty();

	}
	
	@Test
	public void testLazyRegistration() {
		valid1.set(false);
		valid2.set(true);
		
		assertThat(result.isValid()).isTrue(); // no validator is registered at the moment
		
		compositeValidator.registerValidator(validator2);
		assertThat(result.isValid()).isTrue(); // validator2 is valid
		
		compositeValidator.registerValidator(validator1);
		assertThat(result.isValid()).isFalse();
		
		compositeValidator.unregisterValidator(validator1);
		assertThat(result.isValid()).isTrue();
	}
	
	
	private List<String> asStrings(List<ValidationMessage> messages) {
		return messages
				.stream()
				.map(ValidationMessage::getMessage)
				.collect(Collectors.toList());
	}
}
