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

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

/**
 * This {@link Validator} implementation is used to compose multiple other validators.
 * <p>
 * The {@link ValidationStatus} of this validator will contain all messages of all registered validators.
 *
 * @author manuel.mauky
 */
public class CompositeValidator implements Validator {

	private CompositeValidationStatus status = new CompositeValidationStatus();

	private ListProperty<Validator> validators = new SimpleListProperty<>(FXCollections.observableArrayList());
	private Map<Validator, ListChangeListener<ValidationMessage>> listenerMap = new HashMap<>();


	public CompositeValidator() {

		validators.addListener(new ListChangeListener<Validator>() {
			@Override
			public void onChanged(Change<? extends Validator> c) {
				while (c.next()) {

					// When validators are added...
					c.getAddedSubList().forEach(validator -> {

						ObservableList<ValidationMessage> messages = validator.getValidationStatus().getMessages();
						// ... we first add all existing messages to our own validator messages.
						status.addMessage(validator, messages);

						final ListChangeListener<ValidationMessage> changeListener = change -> {
							while (change.next()) {
								// add/remove messages for this particular validator
								status.addMessage(validator, change.getAddedSubList());
								status.removeMessage(validator, change.getRemoved());
							}
						};

						validator.getValidationStatus().getMessages().addListener(changeListener);

						// keep a reference to the listener for a specific validator so we can later use
						// this reference to remove the listener
						listenerMap.put(validator, changeListener);
					});


					c.getRemoved().forEach(validator -> {
						status.removeMessage(validator);

						if (listenerMap.containsKey(validator)) {
							ListChangeListener<ValidationMessage> changeListener = listenerMap.get(validator);

							validator.getValidationStatus().getMessages().removeListener(changeListener);
							listenerMap.remove(validator);
						}
					});
				}
			}
		});

	}

	public CompositeValidator(Validator... validators) {
		this(); // before adding the validators we need to setup the listeners in the default constructor
		addValidators(validators);
	}


	/**
	 * @return an unmodifiable observable list of validators composed by this CompositeValidator.
	 */
	public ObservableList<Validator> getValidators() {
		return FXCollections.unmodifiableObservableList(this.validators);
	}

	public void addValidators(Validator... validators) {
		this.validators.addAll(validators);
	}

	public void removeValidators(Validator... validators) {
		this.validators.removeAll(validators);
	}

	@Override
	public ValidationStatus getValidationStatus() {
		return status;
	}
}
