package de.saxsys.mvvmfx.examples.jigsaw.circle;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.regex.Pattern;

public class CircleViewModel implements ViewModel {

    private static final Pattern VALID_DOUBLE_PATTERN = Pattern.compile("((\\d*)|(\\d+\\.\\d*))");

    private final StringProperty radius = new SimpleStringProperty();
    private final ReadOnlyStringWrapper circularArea = new ReadOnlyStringWrapper("");


    public void initialize() {
        radius.addListener(observable -> calculateCircleArea());
    }

    private void calculateCircleArea() {

        if (radius.get() != null && !radius.get().isEmpty()) {
            double radiusAsDouble = Double.valueOf(radius.get());
            double roundedResult = Math.round(Math.pow(radiusAsDouble, 2) * Math.PI);
            circularArea.set(Double.toString(roundedResult));
        } else {
            circularArea.set("");
        }
    }

    boolean validateDouble(String newText) {

        return VALID_DOUBLE_PATTERN.matcher(newText).matches();
    }

    StringProperty radiusProperty() {
        return radius;
    }

    ReadOnlyStringWrapper circularAreaProperty() {
        return circularArea;
    }
}
