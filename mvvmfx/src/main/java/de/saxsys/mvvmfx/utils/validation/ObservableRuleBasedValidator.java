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

import java.util.ArrayList;
import java.util.List;


/**
 * This {@link Validator} implementation uses observable boolean values as rules. In comparison to the
 * {@link FunctionBasedValidator} with this implementation more complex validation logic can be implemented.
 * <p>
 * It is useful for use cases where:
 *
 * <ul>
 * <li>the validation logic is already available as observable boolean</li>
 * <li>you need to define more multiple rules</li>
 * <li>you need to define complex rules that f.e. are considering multiple fields (cross-field-validation)</li>
 * </ul>
 * <p>
 * You can add multiple rules via the {@link #addRule(ObservableValue, ValidationMessage)} method. Each rule is an
 * observable boolean value. If the rule evaluates to <code>true</code> it is considered to be "valid" and no message
 * will be present in the {@link ValidationStatus} ({@link #getValidationStatus()}). If the rule evaluates to
 * <code>false</code> it is considered to be "invalid". In this case the given {@link ValidationMessage} will be
 * present.
 * <p>
 * If multiple rules are violated, each message will be present.
 */
public class ObservableRuleBasedValidator implements Validator {
	
	private List<ObservableValue<Boolean>> rules = new ArrayList<>();
	
	private ValidationStatus validationStatus = new ValidationStatus();
	
	/**
	 * Add a rule for this validator.
	 * <p>
	 * The rule defines a condition that has to be fulfilled.
	 * <p>
	 * A rule is defined by an observable boolean value. If the rule has a value of <code>true</code> the rule is
	 * "fulfilled". If the rule has a value of <code>false</code> the rule is violated. In this case the given message
	 * object will be added to the status of this validator.
	 * <p>
	 * There are some predefined rules for common use cases in the {@link ObservableRules} class that can be used.
	 * 
	 * @param rule
	 * @param message
	 */
	public void addRule(ObservableValue<Boolean> rule, ValidationMessage message) {
		rules.add(rule);
		
		rule.addListener((observable, oldValue, newValue) -> {
			validateRule(newValue, message);
		});
		
		validateRule(rule.getValue(), message);
	}
	
	private void validateRule(boolean isValid, ValidationMessage message) {
		if (isValid) {
			validationStatus.removeMessage(message);
		} else {
			validationStatus.addMessage(message);
		}
	}
	
	@Override
	public ValidationStatus getValidationStatus() {
		return validationStatus;
	}
	
}
