package de.saxsys.mvvmfx.internal.viewloader.prevent_gc.example1;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PreventGcTestView implements FxmlView<PreventGcTestViewModel> {

	@FXML
	public Label output;
	@FXML
	public TextField input;

	@InjectViewModel
	private PreventGcTestViewModel viewModel;

	public void initialize() {
		output.textProperty().bindBidirectional(viewModel.outputProperty());
		input.textProperty().bindBidirectional(viewModel.inputProperty());
	}
}
