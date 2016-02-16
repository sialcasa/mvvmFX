package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Test;

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
	public void test() {

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
	
}
