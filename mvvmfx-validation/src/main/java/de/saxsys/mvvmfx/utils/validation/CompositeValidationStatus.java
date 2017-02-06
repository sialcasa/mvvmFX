/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	 * The CompositeValidator needs to be able to add and remove {@link ValidationMessage}s for specific validators only.
	 *
	 * Because {@link ValidationMessage} is an immutable value type (overrides equals/hashCode for value equality)
	 * two message instances will be considered to be "equal" when they have the same values even if they are belonging
	 * to different validators. Simply putting the messages into the message list ({@link #getMessagesInternal()})
	 * doesn't work because if a message is removed for one validator, messages with the same values for other validators would be removed too.
	 * <p/>
	 * For this reason we need a special logic here.
	 * To get this working we will maintain a Map that keeps track of all messages for each validator.
	 * Instead of using the actual instances of validator as key and messages as values we will use {@link System#identityHashCode(Object)}
	 * for both. This way we can distinguish between different instances of {@link ValidationMessage} even if
	 * they are considered to be "equal" by equals/hashCode methods.
	 * A second benefit of using identityHashCode is that it minimizes the changes of memory leaks because no references to
	 * actual objects are stored. This is especially important for the validator instance.
	 * <p/>
	 *
	 * Key: {@link System#identityHashCode(Object)} of the validator
	 * Values: A list of {@link System#identityHashCode(Object)} of the validation messages.
	 */
	private Map<Integer, List<Integer>> validatorToMessagesMap = new HashMap<>();


	/**
	 * This class is package private and only used in the {@link CompositeValidator}.
	 * For this use case adding and removing messages is only done in combination with a validator instance.
	 * For this reason the normal methods to add/remove messages are overridden ad no-op methods.
	 */
	@Override
	void addMessage(ValidationMessage message) {
	}

	@Override
	void addMessage(Collection<ValidationMessage> messages) {
	}

	@Override
	void removeMessage(ValidationMessage message) {
	}

	@Override
	void removeMessage(Collection<? extends ValidationMessage> messages) {
	}

	@Override
	void clearMessages() {
	}

	/**
	 * Add a list of validation messages for the specified validator.
	 */
	void addMessage(Validator validator, List<? extends ValidationMessage> messages) {
		if(messages.isEmpty()) {
			return;
		}


		final int validatorHash = System.identityHashCode(validator);

		if(!validatorToMessagesMap.containsKey(validatorHash)){
			validatorToMessagesMap.put(validatorHash, new ArrayList<>());
		}


		final List<Integer> messageHashesOfThisValidator = validatorToMessagesMap.get(validatorHash);

		// add the hashCodes of the messages to the internal map
		messages.stream()
				.map(System::identityHashCode)
				.forEach(messageHashesOfThisValidator::add);

		// add the actual messages to the message list so that they are accessible by the user.
		getMessagesInternal().addAll(messages);
	}

	/*
	 Remove all given messages for the given validator.
	 */
	void removeMessage(final Validator validator, final List<? extends ValidationMessage> messages) {
		if(messages.isEmpty()) {
			return;
		}

		final int validatorHash = System.identityHashCode(validator);

		// if the validator is unknown by the map we haven't stored any messages for it yet that could be removed
		if(validatorToMessagesMap.containsKey(validatorHash)){
			final List<Integer> messageHashesOfThisValidator = validatorToMessagesMap.get(validatorHash);


			final List<Integer> hashesOfMessagesToRemove = messages.stream()
					.filter(m -> { // only those messages that are stored for this validator
						int hash = System.identityHashCode(m);
						return messageHashesOfThisValidator.contains(hash);
					})
					.map(System::identityHashCode) // we only need the hashCode here
					.collect(Collectors.toList());

			// only remove those messages that we have the hashCode stored
			getMessagesInternal().removeIf(message -> {
				int hash = System.identityHashCode(message);
				return hashesOfMessagesToRemove.contains(hash);
			});


			// we need to cleanup our internal map
			messageHashesOfThisValidator.removeAll(hashesOfMessagesToRemove);

			if(messageHashesOfThisValidator.isEmpty()) {
				validatorToMessagesMap.remove(validatorHash);
			}
		}
	}

	/*
	 * Remove all messages for this particular validator.
	 */
	void removeMessage(final Validator validator) {
		final int validatorHash = System.identityHashCode(validator);

		if(validatorToMessagesMap.containsKey(validatorHash)){

			final List<Integer> messageHashesOfThisValidator = validatorToMessagesMap.get(validatorHash);

			getMessagesInternal().removeIf(message -> {
				int hash = System.identityHashCode(message);

				return messageHashesOfThisValidator.contains(hash);
			});

			validatorToMessagesMap.remove(validatorHash);
		}
	}

}
