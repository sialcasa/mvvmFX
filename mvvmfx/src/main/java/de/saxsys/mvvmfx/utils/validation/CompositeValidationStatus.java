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
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is used as {@link ValidationStatus} for {@link CompositeValidator}.
 *
 * In contrast to the basic {@link ValidationStatus} this class not only tracks
 * {@link ValidationMessage} alone but also keeps track of the {@link Validator}s that
 * the messages belong to. This is needed to be able to remove only those messages for
 * a specific validator.
 *
 * @author manuel.mauky
 */
class CompositeValidationStatus extends ValidationStatus {

	/**
	 * This wrapper class is used to additionally store the information of the validator
	 * that the messages belong to.
	 *
	 * Instead of storing the validator instance itself we only store an {@link System#identityHashCode(Object)}
	 * because we don't need the validator itself but only a way to distinguish validator instances.
	 * Using an identity hashcode instead of the actual instance can minimize the possibility of memory leaks.
	 */
	private static class CompositeValidationMessageWrapper extends ValidationMessage {

		private Integer validatorCode;

		CompositeValidationMessageWrapper(ValidationMessage base, Validator validator) {
			super(base.getSeverity(), base.getMessage());
			this.validatorCode = System.identityHashCode(validator);
		}

		Integer getValidatorCode() {
			return validatorCode;
		}
	}


	void addMessage(Validator validator, List<? extends ValidationMessage> messages) {
		/*
			Instead of adding the messages directly to the message list ...
		 */
		getMessagesInternal().addAll(
				messages.stream()
						// ... we wrap them to keep track of the used validator.
					.map(message -> new CompositeValidationMessageWrapper(message, validator))
					.collect(Collectors.toList()));
	}

	/*
	 Remove all given messages for the given validator.
	 */
	void removeMessage(Validator validator, List<? extends ValidationMessage> messages) {
		List<CompositeValidationMessageWrapper> messagesToRemove =
				getMessagesInternal().stream()
					.filter(messages::contains)  // only the given messages
					.filter(message -> (message instanceof CompositeValidationMessageWrapper))
					.map(message -> (CompositeValidationMessageWrapper) message)
					.filter(message -> message.getValidatorCode().equals(System.identityHashCode(validator)))
					.collect(Collectors.toList());

		getMessagesInternal().removeAll(messagesToRemove);
	}

	/*
	 * Remove all messages for this particular validator.
	 */
	void removeMessage(Validator validator) {
		getMessagesInternal().removeIf(validationMessage -> {
			if(validationMessage instanceof CompositeValidationMessageWrapper) {
				CompositeValidationMessageWrapper wrapper = (CompositeValidationMessageWrapper) validationMessage;

				return wrapper.getValidatorCode().equals(System.identityHashCode(validator));
			}

			return false;
		});
	}

}
