package de.saxsys.mvvmfx.examples.jigsaw.rectangle;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.regex.Pattern;

public class RectangleViewModel implements ViewModel {

    private static final Pattern VALID_DOUBLE_PATTERN = Pattern.compile("((\\d*)|(\\d+\\.\\d*))");

    private final StringProperty firstSide = new SimpleStringProperty();
    private final StringProperty secondSide = new SimpleStringProperty();
    private final StringProperty rectangularArea = new SimpleStringProperty();


    public void initialize() {
        firstSide.addListener((observable, oldValue, newValue) -> {
            calculateRectangularArea();
        });

        secondSide.addListener((observable, oldValue, newValue) -> {
            calculateRectangularArea();
        });
    }

    private void calculateRectangularArea() {
        if (firstSide.get() != null && !firstSide.get().isEmpty() && secondSide.get() != null && !secondSide.get().isEmpty()) {
            double result = Math.round(Double.valueOf(firstSide.get()) * Double.valueOf(secondSide.get()));
            rectangularArea.setValue(Double.toString(result));
        } else {
            rectangularArea.setValue("");
        }
    }

    boolean validateDouble(String newText) {
        return VALID_DOUBLE_PATTERN.matcher(newText).matches();
    }

    StringProperty firstSideProperty() {
        return firstSide;
    }

    StringProperty secondSideProperty() {
        return secondSide;
    }

    StringProperty rectangularAreaProperty() {
        return rectangularArea;
    }
}
