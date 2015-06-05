package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;


public class Validator<T> {

    private List<ObservableValue<Boolean>> rules = new ArrayList<>();

    private ValidationResult result = new ValidationResult();

    public Validator() {
    }

    /**
     * Add a rule for this validator. 
     * 
     * The rule defines a condition that has to be fulfilled. 
     * 
     * A rule is defined by an observable boolean value. 
     * If the rule has a value of <code>true</code> the rule is "fulfilled". 
     * If the rule has a value of <code>false</code>
     * the rule is violated. In this case the given message object will be added to the result
     * of this validator.
     * 
     * There are some predefined rules for common use cases in the {@link Rules} class that can be used. 
     * 
     * @param rule
     * @param message
     */
	public void addRule(ObservableValue<Boolean> rule, ValidationMessage message) {
        rules.add(rule);

        rule.addListener((observable, oldValue, newValue) -> {
            validateRule(newValue, message);
        });

        validateRule(rule.getValue(), message);
	}

    private void validateRule(boolean isValid, ValidationMessage message) {
        if(isValid) {
            result.removeMessage(message);
        } else {
            result.addMessage(message);
        }
    }

    /**
     * Returns the {@link ValidationResult} of this Validator. 
     * The properties of the validation result will
     * be updated automatically during the validation performed by this validator.
     */
	public ValidationResult getValidationResult(){
		return result;
	}

}
