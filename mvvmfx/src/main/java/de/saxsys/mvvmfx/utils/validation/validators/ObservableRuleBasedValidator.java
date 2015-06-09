package de.saxsys.mvvmfx.utils.validation.validators;

import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;
import de.saxsys.mvvmfx.utils.validation.Validator;
import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;


/**
 * This {@link Validator} implementation uses observable boolean values as rules. In comparison to the {@link FunctionBasedValidator}
 * with this implementation more complex validation logic can be implemented.
 * <p>
 * It is useful for use cases where:
 * <p>
 * <ul>
 *     <li>the validation logic is already available as observable boolean</li>
 *     <li>you need to define more multiple rules</li>
 *     <li>you need to define complex rules that f.e. are considering multiple fields (cross-field-validation)</li>
 * </ul>
 *
 * You can add multiple rules via the {@link #addRule(ObservableValue, ValidationMessage)} method.
 * Each rule is an observable boolean value. If the rule evaluates to <code>true</code> it is considered to be "valid" and
 * no message will be present in the {@link ValidationStatus} ({@link #getValidationStatus()}).
 * If the rule evaluates to <code>false</code> it is considered to be "invalid".
 * In this case the given {@link ValidationMessage} will be present.
 * <p>
 * If multiple rules are violated, each message will be present.
 */
public class ObservableRuleBasedValidator implements Validator{

    private List<ObservableValue<Boolean>> rules = new ArrayList<>();

    private ValidationStatus result = new ValidationStatus();

    /**
     * Add a rule for this validator. 
     * 
     * The rule defines a condition that has to be fulfilled. 
     * 
     * A rule is defined by an observable boolean value. 
     * If the rule has a value of <code>true</code> the rule is "fulfilled". 
     * If the rule has a value of <code>false</code>
     * the rule is violated. In this case the given message object will be added to the status
     * of this validator.
     * 
     * There are some predefined rules for common use cases in the {@link ObservableRules} class that can be used.
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

	@Override
	public ValidationStatus getValidationStatus(){
		return result;
	}

}
