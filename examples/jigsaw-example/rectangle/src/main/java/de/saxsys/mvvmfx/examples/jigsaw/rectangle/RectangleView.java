package de.saxsys.mvvmfx.examples.jigsaw.rectangle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

public class RectangleView implements FxmlView<RectangleViewModel> {

    @FXML
    private TextField firstSideTextField;
    @FXML
    private TextField secondSideTextField;
    @FXML
    private Label rectangularAreaLabel;

    @InjectViewModel
    private RectangleViewModel rectangleViewModel;

    public void initialize() {
        setupValidation(firstSideTextField);
        setupValidation(secondSideTextField);
        bindViewValues();
    }

    private void setupValidation(TextField textField) {
        final TextFormatter<Double> textFormatter = new TextFormatter<>(new DoubleStringConverter(), 0.0,
                change -> {
                    String newText = change.getControlNewText();
                    if (rectangleViewModel.validateDouble(newText)) {
                        return change;
                    } else {
                        return null;
                    }
                });

        textField.setTextFormatter(textFormatter);
    }

    private void bindViewValues() {
        firstSideTextField.textProperty().bindBidirectional(rectangleViewModel.firstSideProperty());
        secondSideTextField.textProperty().bindBidirectional(rectangleViewModel.secondSideProperty());
        rectangularAreaLabel.textProperty().bind(rectangleViewModel.rectangularAreaProperty());
    }
}
