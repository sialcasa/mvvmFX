package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * This test class shows how you can create custom {@link ValidationMessage}s. 
 */
public class CustomValidationMessageTest {
	
	
	static class MyCustomValidationMessage extends ValidationMessage {
		private final double errorValue;

		public static ValidationMessage factory(double errorValue) {
			return new MyCustomValidationMessage(Severity.ERROR, "Error", errorValue);
		}
		
		public MyCustomValidationMessage(Severity severity, String message, double errorValue) {
			super(severity, message);
			this.errorValue = errorValue;
		}

		public double getErrorValue() {
			return errorValue;
		}
	}
	
	
	@Test
	public void testValidator() {

		StringProperty value = new SimpleStringProperty();
		
		final Predicate<String> predicate = v -> v != null;
		
		Validator validator = new FunctionBasedValidator<>(value, predicate, MyCustomValidationMessage.factory(10.3));

		ValidationStatus validationStatus = validator.getValidationStatus();
		
		
		
		value.setValue(null);


		assertThat(validationStatus.isValid()).isFalse();
		assertThat(validationStatus.getMessages()).hasSize(1);

		ValidationMessage validationMessage = validationStatus.getMessages().get(0);
		
		assertThat(validationMessage).isInstanceOf(MyCustomValidationMessage.class);
		
		MyCustomValidationMessage myCustomValidationMessage = (MyCustomValidationMessage) validationMessage;
		
		assertThat(myCustomValidationMessage.getErrorValue()).isEqualTo(10.3);
	}

	@Test
	public void testCompositeValidator() {
		StringProperty value = new SimpleStringProperty("hallo welt");
		final Predicate<String> notEmptyPredicate = v -> !v.trim().isEmpty();
		final Predicate<String> lengthPredicate = v -> v.length() > 5;

		Validator validator1 = new FunctionBasedValidator<>(value, notEmptyPredicate, MyCustomValidationMessage.factory(10.3));
		Validator validator2 = new FunctionBasedValidator<>(value, lengthPredicate, MyCustomValidationMessage.factory(10.3));


		CompositeValidator compositeValidator = new CompositeValidator(validator1, validator2);

		ValidationStatus validationStatus = compositeValidator.getValidationStatus();


		ObservableList<ValidationMessage> messages = validationStatus.getMessages();

		assertThat(messages).isEmpty();


		value.set("welt");

		assertThat(messages).hasSize(1);

		ValidationMessage validationMessage = messages.get(0);

		assertThat(validationMessage).isInstanceOf(MyCustomValidationMessage.class);
	}
	
}
