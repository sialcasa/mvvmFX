package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * A collection of observable boolean constructors that can be used as rules for the {@link ObservableRuleBasedValidator}.
 */
public class ObservableRules {

	
	public static <T> ObservableBooleanValue fromPredicate(ObservableValue<T> source, Predicate<T> predicate) {
		return Bindings.createBooleanBinding(() -> predicate.test(source.getValue()), source);
	}
    
    public static ObservableBooleanValue notEmpty(ObservableValue<String> source) {
        return Bindings.createBooleanBinding(() -> {
            final String s = source.getValue();
            
            return s != null && !s.trim().isEmpty();
        }, source);
    }

    public static ObservableBooleanValue matches(ObservableValue<String> source, Pattern pattern) {
        return Bindings.createBooleanBinding(() -> {
			final String s = source.getValue();
			return s != null && pattern.matcher(s).matches();
		}, source);
    }

}
