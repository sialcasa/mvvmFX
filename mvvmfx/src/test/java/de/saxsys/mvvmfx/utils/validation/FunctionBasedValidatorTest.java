package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionBasedValidatorTest {
	
	
	@Test
	public void testPredicateVariant() {
		StringProperty value = new SimpleStringProperty();
		
		final Predicate<String> predicate = v -> v != null && !v.isEmpty();
		
		Validator validator = new FunctionBasedValidator<>(value, predicate, ValidationMessage.error("error"));
		
		final ValidationStatus status = validator.getValidationStatus();
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("error");
		
		value.set("something");
		
		assertThat(status.isValid()).isTrue();
		assertThat(status.getErrorMessages()).isEmpty();
	}
	
	
	@Test
	public void testFunctionVariant() {
		StringProperty value = new SimpleStringProperty();
		
		final Function<String, ValidationMessage> function = v -> {
			if (v == null) {
				return ValidationMessage.error("null");
			}
			;
			
			if (v.isEmpty()) {
				return ValidationMessage.error("empty");
			}
			
			return null;
		};
		
		
		Validator validator = new FunctionBasedValidator<>(value, function);
		
		final ValidationStatus status = validator.getValidationStatus();
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("null");
		
		value.set("");
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("empty");
		
		
		value.set("something");
		
		assertThat(status.isValid()).isTrue();
		assertThat(status.getErrorMessages()).isEmpty();
	}
	
	
}
