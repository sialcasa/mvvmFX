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

/**
 * This interface is implemented by specific validators. Each validator has to provide a reactive
 * {@link ValidationStatus} that's is updated by the validator implementation when the validation is executed (f.e. when
 * the user has changed an input value).
 *
 * @author manuel.mauky
 */
public interface Validator {
	
	/**
	 * Returns the validation status of this validator. The status will be updated when the validator re-validates the
	 * inputs of the user.
	 *
	 * @return the state.
	 */
	ValidationStatus getValidationStatus();
	
}
