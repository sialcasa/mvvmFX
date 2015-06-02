package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.value.ObservableBooleanValue;

import java.util.ArrayList;
import java.util.List;


public class Validator<T> {

    private List<ObservableBooleanValue> rules = new ArrayList<>();

    private ValidationResult result = new ValidationResult();

    public Validator() {
    }
	
	public void addRule(ObservableBooleanValue rule, ValidationMessage message) {
        rules.add(rule);

        rule.addListener((observable, oldValue, newValue) -> {
            validateRule(newValue, message);
        });

        validateRule(rule.get(), message);
	}

    private void validateRule(boolean value, ValidationMessage message) {
        System.out.println("validating rule:" + value);
        if(value) {
            result.addMessage(message);
        } else {
            result.removeMessage(message);
        }
    }
	
	
	public ValidationResult getValidationResult(){
		return result;
	}

}
