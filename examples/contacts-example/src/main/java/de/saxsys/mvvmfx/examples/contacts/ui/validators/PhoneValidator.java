package de.saxsys.mvvmfx.examples.contacts.ui.validators;

import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.ObservableRuleBasedValidator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableValue;

import java.util.regex.Pattern;

/**
 * @author manuel.mauky
 */
public class PhoneValidator extends ObservableRuleBasedValidator {

    private static final Pattern SIMPLE_PHONE_PATTERN = Pattern.compile("\\+?[0-9\\s]{3,20}");

    public PhoneValidator(ObservableValue<String> number, String message) {

        final BooleanBinding phonePatternMatches = Bindings.createBooleanBinding(() -> {
            final String input = number.getValue();

            if (input == null || input.trim().isEmpty()) {
                return true;
            }

            return SIMPLE_PHONE_PATTERN.matcher(input).matches();
        }, number);

        addRule(phonePatternMatches, ValidationMessage.error(message));
    }
    
}
