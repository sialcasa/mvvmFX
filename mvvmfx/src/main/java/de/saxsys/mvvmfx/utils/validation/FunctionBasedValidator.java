/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.value.ObservableValue;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * This {@link Validator} implementation uses functions to validate the values of an observable. You need to define a
 * observable value as source that contains the value that should be validated.
 * <p>
 * There are two "flavours" of using this validator: Using a {@link Predicate} or a {@link Function} for validation.
 * <p>
 * The variant with Predicate is used for simple use cases where you provide a predicate that simply tells the
 * validator, if the given value is valid or not. If it is invalid, the given {@link ValidationMessage} will be present
 * in the {@link ValidationStatus} of this validator.
 * <p>
 * The variant with Function is used for use cases where different messages should be shown under specific conditions.
 * Instead of only returning <code>true</code> or <code>false</code> the function has to return a
 * {@link ValidationMessage} for a given input value if it is invalid. The returned message will then be present in the
 * validation status. If the input value is valid and therefore no validation message should be shown, the function has
 * to return <code>null</code> instead.
 *
 * <p>
 * <br>
 * For more complex validation logic like cross field validation you can use the {@link ObservableRuleBasedValidator} as
 * an alternative.
 *
 * @param <T>
 *            the generic value of the source observable.
 */
public class FunctionBasedValidator<T> implements Validator {
	
	private ValidationStatus validationStatus = new ValidationStatus();
	
	private Function<T, Optional<ValidationMessage>> validateFunction;
	
	private FunctionBasedValidator(ObservableValue<T> source) {
		source.addListener((observable, oldValue, newValue) -> {
			validate(newValue);
		});
	}
	
	
	/**
	 * Creates a validator that uses a {@link Function} for validation. The function has to return a
	 * {@link ValidationMessage} for a given input value or <code>null</code> if the value is valid.
	 *
	 * @param source
	 *            the observable value that will be validated.
	 * @param function
	 *            the validation function.
	 */
	public FunctionBasedValidator(ObservableValue<T> source, Function<T, ValidationMessage> function) {
		this(source);
		
		validateFunction = value -> Optional.ofNullable(function.apply(value));
		
		validate(source.getValue());
	}
	
	/**
	 * Creates a validator that uses a {@link Predicate} for validation. The predicate has to return <code>true</code>
	 * if the input value is valid. Otherwise <code>false</code>.
	 * <p>
	 * When the predicate returns <code>false</code>, the given validation message will be used.
	 *
	 * @param source
	 *            the observable value that will be validated.
	 * @param predicate
	 *            the validation predicate.
	 * @param message
	 *            the message that will be used when the predicate doesn't match
	 */
	public FunctionBasedValidator(ObservableValue<T> source, Predicate<T> predicate, ValidationMessage message) {
		this(source);
		
		validateFunction = value -> Optional.ofNullable(predicate.test(value) ? null : message);
		
		validate(source.getValue());
	}
	
	private void validate(T newValue) {
		validationStatus.clearMessages();
		Optional<ValidationMessage> message = validateFunction.apply(newValue);
		message.ifPresent(validationStatus::addMessage);
	}
	
	@Override
	public ValidationStatus getValidationStatus() {
		return validationStatus;
	}
}
