package de.saxsys.mvvmfx.internal.viewloader.prevent_gc.example1;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.PreventGarbageCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * This codeBehind class is similar to {@link PreventGcTestView} with
 * the only difference that this class implements {@link de.saxsys.mvvmfx.PreventGarbageCollection}.
 */
public class PreventGc2TestView implements FxmlView<PreventGcTestViewModel>, PreventGarbageCollection {

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
