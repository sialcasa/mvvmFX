package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableRulesValidatorTest {
	
	private BooleanProperty rule1;
	private BooleanProperty rule2;
	
	private ObservableRuleBasedValidator validator;
	
	@Before
	public void setup() {
		rule1 = new SimpleBooleanProperty();
		rule2 = new SimpleBooleanProperty();
		
		validator = new ObservableRuleBasedValidator();
	}
	
	@Test
	public void test() {
		
		final ValidationStatus status = validator.getValidationStatus();
		assertThat(status.isValid()).isTrue();
		
		rule1.setValue(false);
		
		validator.addRule(rule1, ValidationMessage.error("error"));
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("error");
		
		rule1.setValue(true);
		assertThat(status.isValid()).isTrue();
		assertThat(status.getErrorMessages()).isEmpty();
		
		rule2.setValue(false);
		
		validator.addRule(rule2, ValidationMessage.warning("warning"));
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).isEmpty();
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getWarningMessages().get(0).getMessage()).isEqualTo("warning");
		
		
		
		rule1.setValue(false);
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("error");
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getWarningMessages().get(0).getMessage()).isEqualTo("warning");
		
		rule1.setValue(true);
		rule2.setValue(true);
		assertThat(status.isValid()).isTrue();
		assertThat(status.getErrorMessages()).isEmpty();
		assertThat(status.getWarningMessages()).isEmpty();
	}
	
	
	
}
