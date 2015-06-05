package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author manuel.mauky
 */
public class CompositeValidationResult extends ValidationResult {
	
	private ListProperty<ValidationResult> subResults = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	private Map<ValidationResult, ListChangeListener<ValidationMessage>> changeListeners = new HashMap<>();
	
	public CompositeValidationResult() {
		subResults.addListener((ListChangeListener<ValidationResult>) c -> {
			while(c.next()) {
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
	
	void addResults(ValidationResult... results) {
		subResults.addAll(results);
	}
	
	void addResults(List<ValidationResult> results) {
		subResults.addAll(results);
	}
	
	void removeResults(ValidationResult...results) {
		subResults.removeAll(results);
	}
	
	void removeResults(List<ValidationResult> results) {
		subResults.removeAll(results);
	}
	
}
