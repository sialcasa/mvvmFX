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
 * This class represents a single validation message for an error or a warning. It consists of a string message and a
 * {@link Severity}.
 *
 * @author manuel.mauky
 */
public class ValidationMessage {
	
	private final String message;
	
	private final Severity severity;
	
	ValidationMessage(Severity severity, String message) {
		this.message = message;
		this.severity = severity;
	}
	
	
	public static ValidationMessage warning(String message) {
		return new ValidationMessage(Severity.WARNING, message);
	}
	
	public static ValidationMessage error(String message) {
		return new ValidationMessage(Severity.ERROR, message);
	}
	
	public String getMessage() {
		return message;
	}
	
	public Severity getSeverity() {
		return severity;
	}
	
	@Override
	public String toString() {
		return "ValidationMessage{" +
				"message='" + message + '\'' +
				", severity=" + severity +
				'}';
	}
}
