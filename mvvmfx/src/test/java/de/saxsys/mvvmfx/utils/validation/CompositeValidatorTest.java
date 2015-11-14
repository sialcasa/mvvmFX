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
	
	private ValidationStatus status;
	
	private BooleanProperty valid1 = new SimpleBooleanProperty();
	private BooleanProperty valid2 = new SimpleBooleanProperty();
	private CompositeValidator compositeValidator;
	private ObservableRuleBasedValidator validator1;
	private ObservableRuleBasedValidator validator2;
	
	@Before
	public void setup() {
		validator1 = new ObservableRuleBasedValidator();
		validator1.addRule(valid1, ValidationMessage.error("error1"));
		
		validator2 = new ObservableRuleBasedValidator();
		validator2.addRule(valid2, ValidationMessage.warning("warning2"));
		
		
		compositeValidator = new CompositeValidator();
		status = compositeValidator.getValidationStatus();
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
	
	
	private List<String> asStrings(List<ValidationMessage> messages) {
		return messages
				.stream()
				.map(ValidationMessage::getMessage)
				.collect(Collectors.toList());
	}
}
