package de.saxsys.jfx.mvvmfx.helloworld;

import de.saxsys.jfx.mvvm.base.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloWorldView extends View<HelloWorldViewModel>{

    @FXML
    private Label helloLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.textProperty().bind(getViewModel().helloMessage());
    }
}
