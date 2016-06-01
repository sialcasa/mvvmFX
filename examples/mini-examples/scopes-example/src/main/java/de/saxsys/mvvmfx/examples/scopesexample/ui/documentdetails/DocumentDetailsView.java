package de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DocumentDetailsView implements FxmlView<DocumentDetailsViewModel> {

	@FXML
	public Label titleLabel;

	@FXML
	public Label descriptionLabel;

	@FXML
	public Label isSelectedLabel;


	@InjectViewModel
	private DocumentDetailsViewModel viewModel;


	public void initialize() {
		titleLabel.textProperty().bindBidirectional(viewModel.titleProperty());
		descriptionLabel.textProperty().bindBidirectional(viewModel.descriptionProperty());

		isSelectedLabel.textProperty().bind(
				Bindings
						.when(viewModel.isSelectedProperty())
						.then("This Document is selected")
						.otherwise("This Document is not selected"));
	}

}
