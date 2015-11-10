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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author manuel.mauky
 */
class CompositeValidationResult extends ValidationStatus {
	
	private ListProperty<ValidationStatus> subResults = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	private Map<ValidationStatus, ListChangeListener<ValidationMessage>> changeListeners = new HashMap<>();
	
	public CompositeValidationResult() {
		subResults.addListener((ListChangeListener<ValidationStatus>) c -> {
			while (c.next()) {
				c.getAddedSubList().forEach(result -> {
					CompositeValidationResult.this.addMessage(result.getMessages());
					
					final ListChangeListener<ValidationMessage> changeListener = change -> {
						while (change.next()) {
							change.getAddedSubList().forEach(CompositeValidationResult.this::addMessage);
							change.getRemoved().forEach(CompositeValidationResult.this::removeMessage);
						}
					};
					result.getMessages().addListener(changeListener);
					changeListeners.put(result, changeListener);
				});
				c.getRemoved().forEach(result -> {
					CompositeValidationResult.this.removeMessage(result.getMessages());
					result.getMessages().removeListener(changeListeners.get(result));
				});
			}
		});
	}
	
	void addResults(ValidationStatus... results) {
		subResults.addAll(results);
	}
	
	void addResults(List<ValidationStatus> results) {
		subResults.addAll(results);
	}
	
	void removeResults(ValidationStatus... results) {
		subResults.removeAll(results);
	}
	
	void removeResults(List<ValidationStatus> results) {
		subResults.removeAll(results);
	}
	
}
