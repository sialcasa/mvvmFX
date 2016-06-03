package de.saxsys.mvvmfx.examples.scopesexample.ui.overview.detail;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class OverviewDetailView implements FxmlView<OverviewDetailViewModel> {

	@FXML
	public TextField titleField;
	@FXML
	public Label titleLabel;


	@FXML
	public HBox displayBlock;
	@FXML
	public HBox editBlock;

	@InjectViewModel
	private OverviewDetailViewModel viewModel;

	public void initialize() {
		titleField.textProperty().bindBidirectional(viewModel.titleProperty());
		titleLabel.textProperty().bind(viewModel.titleProperty());

		displayBlock.visibleProperty().bind(viewModel.editModeProperty().not());
		displayBlock.managedProperty().bind(viewModel.editModeProperty().not());
		editBlock.visibleProperty().bind(viewModel.editModeProperty());
		editBlock.managedProperty().bind(viewModel.editModeProperty());
	}

	public void edit() {
		viewModel.edit();
	}

	public void save() {
		viewModel.save();
	}
}
