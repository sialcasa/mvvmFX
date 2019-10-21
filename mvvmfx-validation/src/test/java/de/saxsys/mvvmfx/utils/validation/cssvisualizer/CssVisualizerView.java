package de.saxsys.mvvmfx.utils.validation.cssvisualizer;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.validation.visualization.ValidationVisualizer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CssVisualizerView implements FxmlView<CssVisualizerViewModel> {

	@FXML
	private TextField email;

	@InjectViewModel
	private CssVisualizerViewModel viewModel;

	private ValidationVisualizer visualizer = new CssVisualizer("error", "valid", "required");

	public void initialize() {
		email.textProperty().bindBidirectional(viewModel.emailAddressProperty());

		visualizer.initVisualization(viewModel.getValidationStatus(), email, true);
	}
}
