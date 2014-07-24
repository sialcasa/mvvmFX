package de.saxsys.jfx.mvvm.viewloader.example;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.jfx.mvvm.api.FxmlView;

/**
 * This class is used as example for a negative test case. There is no fxml file that matches the naming conventions for
 * this View.
 */
public class InvalidFxmlTestView implements FxmlView<TestViewModel> {
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}
}
