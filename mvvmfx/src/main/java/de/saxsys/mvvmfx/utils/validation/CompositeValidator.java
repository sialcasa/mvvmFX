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

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This {@link Validator} implementation is used to compose multiple other validators.
 * <p>
 * The {@link ValidationStatus} of this validator will contain all messages of all registered validators.
 *
 *
 * @author manuel.mauky
 */
public class CompositeValidator implements Validator {
	
	private CompositeValidationResult validationStatus = new CompositeValidationResult();
	
	public CompositeValidator() {
	}
	
	public CompositeValidator(Validator... validators) {
		addValidators(validators);
	}
	
	
	public void addValidators(Validator... validators) {
		validationStatus.addResults(Stream.of(validators)
				.map(Validator::getValidationStatus)
				.collect(Collectors.toList()));
	}
	
	public void removeValidators(Validator... validators) {
		validationStatus.removeResults(Stream.of(validators)
				.map(Validator::getValidationStatus)
				.collect(Collectors.toList()));
	}
	
	@Override
	public ValidationStatus getValidationStatus() {
		return validationStatus;
	}
}
