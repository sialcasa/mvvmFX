package de.saxsys.mvvmfx.internal.viewloader.builderfactory;

import javafx.scene.control.TextField;

/**
 * A custom TextField to test builder factories
 */
public class CustomTextField extends TextField {

	private final String special;

	/**
	 * Due to this constructor the custom control can't be used directly in FXML
	 * without the builder factory.
	 */
	public CustomTextField(String special) {
		this.special = special;
	}

	public String getSpecial() {
		return special;
	}
}
