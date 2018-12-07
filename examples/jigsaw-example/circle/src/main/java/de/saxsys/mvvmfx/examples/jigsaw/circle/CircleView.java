package de.saxsys.mvvmfx.examples.jigsaw.circle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

public class CircleView implements FxmlView<CircleViewModel> {

    @FXML
    private TextField radiusTextField;

    @FXML
    private Label circleAreaLabel;

    @InjectViewModel
    private CircleViewModel circleViewModel;


    public void initialize() {
        setupValidation();
        bindViewValues();
    }

    private void setupValidation() {
        TextFormatter<Double> textFormatter = new TextFormatter<>(new DoubleStringConverter(), 0.0,
                change -> {
                    String newText = change.getControlNewText();
                    if (circleViewModel.validateDouble(newText)) {
                        return change;
                    } else {
                        return null;
                    }
                });

        radiusTextField.setTextFormatter(textFormatter);
    }

    private void bindViewValues() {
        radiusTextField.textProperty().bindBidirectional(circleViewModel.radiusProperty());
        circleAreaLabel.textProperty().bind(circleViewModel.circularAreaProperty());
    }
}
