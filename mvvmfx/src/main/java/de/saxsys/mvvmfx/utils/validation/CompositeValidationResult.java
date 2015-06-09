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
