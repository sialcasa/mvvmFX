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
package de.saxsys.mvvmfx.utils.validation.crossfieldexample;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.validation.ObservableRuleBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author manuel.mauky
 */
public class RegisterFormViewModel implements ViewModel {
	
	private StringProperty password = new SimpleStringProperty();
	private StringProperty passwordRepeat = new SimpleStringProperty();
	
	
	private ObservableRuleBasedValidator passwordValidator = new ObservableRuleBasedValidator();
	
	public RegisterFormViewModel() {
		final BooleanBinding rule1 = password.isNotEmpty();
		final BooleanBinding rule2 = passwordRepeat.isNotEmpty();
		final BooleanBinding rule3 = password.isEqualTo(passwordRepeat);
		
		passwordValidator.addRule(rule1, ValidationMessage.error("Please enter a password"));
		passwordValidator.addRule(rule2, ValidationMessage.error("Please enter the password a second time"));
		passwordValidator.addRule(rule3, ValidationMessage.error("Both passwords need to be the same"));
	}
	
	public ValidationStatus getValidation() {
		return passwordValidator.getValidationStatus();
	}
	
	public String getPassword() {
		return password.get();
	}
	
	public StringProperty passwordProperty() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password.set(password);
	}
	
	public String getPasswordRepeat() {
		return passwordRepeat.get();
	}
	
	public StringProperty passwordRepeatProperty() {
		return passwordRepeat;
	}
	
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat.set(passwordRepeat);
	}
	
}
