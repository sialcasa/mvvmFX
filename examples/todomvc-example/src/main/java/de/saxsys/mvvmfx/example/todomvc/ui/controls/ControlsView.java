package de.saxsys.mvvmfx.example.todomvc.ui.controls;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author manuel.mauky
 */
public class ControlsView implements FxmlView<ControlsViewModel> {
	
	@FXML
	public Label itemsLeftLabel;
	
	@InjectViewModel
	private ControlsViewModel viewModel;
	
	public void initialize() {
		itemsLeftLabel.textProperty().bind(viewModel.itemsLeftLabelTextProperty());
	}
	
	public void all() {
		viewModel.all();
	}
	
	public void active() {
		viewModel.active();
	}
	
	public void completed() {
		viewModel.completed();
	}
}
