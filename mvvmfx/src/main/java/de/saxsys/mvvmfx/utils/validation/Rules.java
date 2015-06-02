package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;

import java.util.regex.Pattern;

public class Rules {

    
    public static ObservableBooleanValue notEmpty(ObservableStringValue source) {
        return Bindings.createBooleanBinding(() -> {
            final String s = source.get();
            
            return s != null && !s.trim().isEmpty();
        }, source);
    }

    public static ObservableBooleanValue matches(ObservableStringValue source, Pattern pattern) {
        return Bindings.createBooleanBinding(() -> {
            final String s = source.get();
            if(s == null) {
                return false;
            } else {
                return pattern.matcher(s).matches();
            }
        }, source);
    }

}
