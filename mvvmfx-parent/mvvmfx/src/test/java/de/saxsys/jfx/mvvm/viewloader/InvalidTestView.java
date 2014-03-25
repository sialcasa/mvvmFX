package de.saxsys.jfx.mvvm.viewloader;

import de.saxsys.jfx.mvvm.base.view.View;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is used as example for a negative test case. There is no fxml file that matches the naming conventions for
 * this View.
 */
public class InvalidTestView extends View<TestViewModel> {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
