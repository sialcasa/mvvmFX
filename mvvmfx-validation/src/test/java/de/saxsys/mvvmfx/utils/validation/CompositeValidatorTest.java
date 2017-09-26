/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.validation;

import com.google.common.base.Strings;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author manuel.mauky
 */
public class CompositeValidatorTest {

	private ValidationStatus status;

	private BooleanProperty valid1 = new SimpleBooleanProperty();
	private BooleanProperty valid2 = new SimpleBooleanProperty();
	private CompositeValidator compositeValidator;
	private ObservableRuleBasedValidator validator1;
	private ObservableRuleBasedValidator validator2;

	@BeforeEach
	public void setup() {
		validator1 = new ObservableRuleBasedValidator();
		validator1.addRule(valid1, ValidationMessage.error("error1"));

		validator2 = new ObservableRuleBasedValidator();
		validator2.addRule(valid2, ValidationMessage.warning("warning2"));


		compositeValidator = new CompositeValidator();
		status = compositeValidator.getValidationStatus();
	}

	/**
	 * Test that the composite validator also works when the base validators are passed to the constructor.
	 */
	@Test
	public void testValidatorConstructor() {
		valid1.setValue(false);
		valid2.setValue(false);

		CompositeValidator newValidator = new CompositeValidator(validator1, validator2);
		assertThat(newValidator.getValidationStatus().isValid()).isFalse();
		assertThat(newValidator.getValidationStatus().getMessages()).hasSize(2);
	}

	@Test
	public void testValidation() {
		compositeValidator.addValidators(validator1, validator2);

		valid1.set(true);
		valid2.set(true);

		assertThat(status.isValid()).isTrue();

		valid1.setValue(false);

		assertThat(status.isValid()).isFalse();
		assertThat(asStrings(status.getErrorMessages())).containsOnly("error1");
		assertThat(asStrings(status.getWarningMessages())).isEmpty();
		assertThat(asStrings(status.getMessages())).containsOnly("error1");

		valid2.setValue(false);
		assertThat(status.isValid()).isFalse();
		assertThat(asStrings(status.getErrorMessages())).containsOnly("error1");
		assertThat(asStrings(status.getWarningMessages())).containsOnly("warning2");
		assertThat(asStrings(status.getMessages())).containsOnly("error1", "warning2");

		valid1.setValue(true);
		assertThat(status.isValid()).isFalse();
		assertThat(asStrings(status.getErrorMessages())).isEmpty();
		assertThat(asStrings(status.getWarningMessages())).containsOnly("warning2");
		assertThat(asStrings(status.getMessages())).containsOnly("warning2");

		valid2.setValue(true);
		assertThat(status.isValid()).isTrue();
		assertThat(asStrings(status.getErrorMessages())).isEmpty();
		assertThat(asStrings(status.getWarningMessages())).isEmpty();
		assertThat(asStrings(status.getMessages())).isEmpty();

	}

	@Test
	public void testLazyRegistration() {
		valid1.set(false);
		valid2.set(true);

		assertThat(status.isValid()).isTrue(); // no validator is registered at the moment

		compositeValidator.addValidators(validator2);
		assertThat(status.isValid()).isTrue(); // validator2 is valid

		compositeValidator.addValidators(validator1);
		assertThat(status.isValid()).isFalse();

		compositeValidator.removeValidators(validator1);
		assertThat(status.isValid()).isTrue();
	}


	/**
	 * This test is used to verify a previously existing bug
	 * (<a href="https://github.com/sialcasa/mvvmFX/issues/398">398</a>) is fixed.
	 *
	 * The wrong behaviour was happening when two validators where using the a ValidationMessage
	 * with the same values (for example {@link Severity#ERROR} and a string message "error").
	 * In this case when one of the validators was removed the message for the second validator
	 * was also gone.
	 */
	@Test
	public void testUpdateResultWhenValidatorsAreRemoved() {
		final SimpleStringProperty stringPropertyOne = new SimpleStringProperty("");

		final FunctionBasedValidator<String> validatorOne = new FunctionBasedValidator<>(stringPropertyOne, s -> {
			if (s.isEmpty()) {
				return new ValidationMessage(Severity.ERROR, "empty1");
			} else {
				return null;
			}
		});

		final SimpleStringProperty stringPropertyTwo = new SimpleStringProperty("");

		final FunctionBasedValidator<String> validatorTwo = new FunctionBasedValidator<>(stringPropertyTwo, s -> {
			if (s.isEmpty()) {
				return new ValidationMessage(Severity.ERROR, "empty1");
			} else {
				return null;
			}
		});

		final CompositeValidator compositeValidator = new CompositeValidator();


		assertThat(compositeValidator.getValidationStatus().isValid()).isTrue();
		assertThat(compositeValidator.getValidationStatus().getErrorMessages()).isEmpty();


		compositeValidator.addValidators(validatorOne);

		assertThat(compositeValidator.getValidationStatus().isValid()).isFalse();
		assertThat(compositeValidator.getValidationStatus().getErrorMessages()).hasSize(1);

		compositeValidator.addValidators(validatorTwo);

		assertThat(compositeValidator.getValidationStatus().isValid()).isFalse();
		assertThat(compositeValidator.getValidationStatus().getErrorMessages()).hasSize(2);


		compositeValidator.removeValidators(validatorTwo);
		assertThat(compositeValidator.getValidationStatus().isValid()).isFalse();
		assertThat(compositeValidator.getValidationStatus().getErrorMessages()).hasSize(1);


		compositeValidator.removeValidators(validatorOne);
		assertThat(compositeValidator.getValidationStatus().isValid()).isTrue();
		assertThat(compositeValidator.getValidationStatus().getErrorMessages()).isEmpty();
	}

	/**
	 * Verify that it's easy possible to assert on existing validation messages
	 * by creating new ValidationMessage instances with the expected values on the fly.
	 * This is needed to write useful unit tests for users.
	 */
	@Test
	public void testTestability() {

		final SimpleStringProperty stringPropertyOne = new SimpleStringProperty("");

		final FunctionBasedValidator<String> validatorOne = new FunctionBasedValidator<>(stringPropertyOne, s -> {
			if (s.isEmpty()) {
				return new ValidationMessage(Severity.ERROR, "empty1");
			} else {
				return null;
			}
		});


		CompositeValidator compositeValidator = new CompositeValidator();
		compositeValidator.addValidators(validatorOne);

		// It's possible to write an assert for the existing of a test by creating a new validation message object with the same values.
		// This makes writing unit tests easier and more readable.
		assertThat(compositeValidator.getValidationStatus().getMessages()).contains(ValidationMessage.error("empty1"));


	}

	/**
	 * Issue #413
	 */
	@Test
	public void validatorsMayNotDeleteEachOthersValidationMessages() {
		final StringProperty prop1 = new SimpleStringProperty();
		final StringProperty prop2 = new SimpleStringProperty();
		final Validator notEmpty1 = new FunctionBasedValidator<>(prop1, v -> {
			if (Strings.isNullOrEmpty(v)) {
				return ValidationMessage.error("msg");
			}
			return null;
		});
		final Validator notEmpty2 = new FunctionBasedValidator<>(prop2, v -> {
			if (Strings.isNullOrEmpty(v)) {
				return ValidationMessage.error("msg");
			}
			return null;
		});
		final CompositeValidator compositeValidator = new CompositeValidator(notEmpty1, notEmpty2);
		prop1.set("");
		prop2.set("");
		prop1.set("a");
		assertThat(notEmpty1.getValidationStatus().isValid()).isTrue();
		assertThat(notEmpty2.getValidationStatus().isValid()).isFalse();
		assertThat(compositeValidator.getValidationStatus().isValid()).isFalse();
	}


	private List<String> asStrings(List<ValidationMessage> messages) {
		return messages
				.stream()
				.map(ValidationMessage::getMessage)
				.collect(Collectors.toList());
	}


    /**
    * Issue #413
    */
    @Test
    public void validatorPercentageTest(){

        final IntegerProperty integerProperty1 = new SimpleIntegerProperty(30);
        final IntegerProperty integerProperty2 = new SimpleIntegerProperty(-20);
        final IntegerProperty integerProperty3 = new SimpleIntegerProperty(35);
		final IntegerProperty integerProperty4 = new SimpleIntegerProperty(55);

        Predicate<Number> predicate1 = v -> v.doubleValue() > 50;

		final Validator validator1 = new FunctionBasedValidator<>(integerProperty1, predicate1, ValidationMessage.error("Value must be bigger than 50"));
        final Validator validator2 = new FunctionBasedValidator<>(integerProperty2, predicate1, ValidationMessage.error("Value must be bigger than 50"));
        final Validator validator3 = new FunctionBasedValidator<>(integerProperty3, predicate1, ValidationMessage.error("Value must be bigger than 50"));
		final Validator validator4 = new FunctionBasedValidator<>(integerProperty4, predicate1, ValidationMessage.error("Value must be bigger than 50"));

        final CompositeValidator compositeValidator = new CompositeValidator(validator1, validator2, validator3);
        assertThat(compositeValidator.getValidationStatus().getMessages()).hasSize(3);

		IntegerBinding percentage = Bindings.createIntegerBinding(() -> {
            int numberOfValidators = compositeValidator.getValidators().size();

            if (numberOfValidators == 0) {
                return 100;
            } else {
                int numberOfValidValidators = (int) compositeValidator.getValidators().stream()
                        .map(Validator::getValidationStatus)
                        .filter(ValidationStatus::isValid)
                        .count();

                return numberOfValidValidators * 100 / numberOfValidators;
            }
        }, compositeValidator.getValidationStatus().getMessages(), compositeValidator.getValidators());


        assertThat(percentage.intValue()).isEqualTo(0);

        // change values
        integerProperty1.setValue(70);
        assertThat(percentage.intValue()).isEqualTo(33);
        integerProperty1.setValue(0);
        assertThat(percentage.intValue()).isEqualTo(0);


		integerProperty2.setValue(100);
		assertThat(percentage.intValue()).isEqualTo(33);

		integerProperty1.setValue(70);
		assertThat(percentage.intValue()).isEqualTo(66);

        integerProperty2.setValue(50);
        assertThat(percentage.intValue()).isEqualTo(33);

        // add new Validator
        compositeValidator.addValidators(validator4);
        assertThat(percentage.intValue()).isEqualTo(50);

        // 0% valid
        integerProperty1.setValue(50);
        integerProperty4.setValue(50);
        assertThat(percentage.intValue()).isEqualTo(0);

        // 100% valid
        integerProperty1.setValue(51);
        integerProperty2.setValue(100);
        integerProperty3.setValue(80);
        integerProperty4.setValue(75);
        assertThat(percentage.intValue()).isEqualTo(100);

		// remove validator
		compositeValidator.removeValidators(validator1);
		assertThat(percentage.intValue()).isEqualTo(100);
		integerProperty2.setValue(30);
		assertThat(percentage.intValue()).isEqualTo(66);

    }
}
