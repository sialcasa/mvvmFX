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
