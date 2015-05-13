package de.saxsys.mvvmfx.resourcebundle.global;

import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ResourceBundle;

/**
 * @author manuel.mauky
 */
public class TestView implements FxmlView<TestViewModel> {

	@FXML
	public Label global_label;
	@FXML
	public Label other_label;
	@FXML
	public Label label;

	public ResourceBundle resources;
	
}
