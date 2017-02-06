package de.saxsys.mvvmfx.scopes.example6;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TabView implements FxmlView<TabViewModel> {
	@FXML
	public Label content;

	@InjectViewModel
	private TabViewModel viewModel;

	public void initialize() {
		content.textProperty().bind(viewModel.labelTextProperty());
	}
}
