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

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.Collection;
import java.util.Optional;


/**
 * This class represents the state of a {@link Validator}.
 * <p>
 * This class is reactive, which means that it's values will represent the current validation status. When the
 * validation status changes the observable lists for the messages will be updated automatically.
 *
 *
 * @author manuel.mauky
 */
public class ValidationStatus {
	
	private ListProperty<ValidationMessage> messages = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObservableList<ValidationMessage> unmodifiableMessages = FXCollections.unmodifiableObservableList(messages);
	private ObservableList<ValidationMessage> errorMessages = FXCollections.unmodifiableObservableList(
			new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.ERROR)));
	private ObservableList<ValidationMessage> warningMessages = FXCollections.unmodifiableObservableList(
			new FilteredList<>(messages, message -> message.getSeverity().equals(Severity.WARNING)));
	
	
	void addMessage(ValidationMessage message) {
		messages.add(message);
	}
	
	void addMessage(Collection<ValidationMessage> messages) {
		this.messages.addAll(messages);
	}
	
	void removeMessage(ValidationMessage message) {
		messages.remove(message);
	}
	
	void removeMessage(Collection<? extends ValidationMessage> messages) {
		this.messages.removeAll(messages);
	}
	
	void clearMessages() {
		messages.clear();
	}
	
	
	public ObservableList<ValidationMessage> getMessages() {
		return unmodifiableMessages;
	}
	
	public ObservableList<ValidationMessage> getErrorMessages() {
		return errorMessages;
	}
	
	public ObservableList<ValidationMessage> getWarningMessages() {
		return warningMessages;
	}
	
	/**
	 * @return <code>true</code> if there are no validation messages present.
	 */
	public ReadOnlyBooleanProperty validProperty() {
		return messages.emptyProperty();
	}
	
	public boolean isValid() {
		return validProperty().get();
	}
	
	/**
	 * Returns the message with the highest priority using the following algorithm: - if there are messages with
	 * {@link Severity#ERROR}, take the first one. - otherwise, if there are messages with {@link Severity#WARNING},
	 * take the first one. - otherwise, an empty Optional is returned.
	 * 
	 * @return an Optional containing the ValidationMessage or an empty Optional.
	 */
	public Optional<ValidationMessage> getHighestMessage() {
		final Optional<ValidationMessage> error = messages.stream()
				.filter(message -> message.getSeverity().equals(Severity.ERROR))
				.findFirst();
		
		if (error.isPresent()) {
			return error;
		} else {
			final Optional<ValidationMessage> warning = messages.stream()
					.filter(message -> message.getSeverity().equals(Severity.WARNING))
					.findFirst();
			
			return warning;
		}
	}
	
}
