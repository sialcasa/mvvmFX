package de.saxsys.mvvmfx.examples.helloworld;

import de.saxsys.mvvmfx.FxmlPath;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

@FxmlPath("/some/other/path/HalloWelt.fxml")
public class HelloView implements FxmlView<HelloViewModel> {
	@FXML
	public Label helloLabel;

	@InjectViewModel
	private HelloViewModel viewModel;

	public void initialize() {
		helloLabel.textProperty().bind(viewModel.helloMessage());
	}
}
