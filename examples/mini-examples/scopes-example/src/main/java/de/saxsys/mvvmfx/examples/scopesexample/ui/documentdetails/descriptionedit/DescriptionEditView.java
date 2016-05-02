package de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails.descriptionedit;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DescriptionEditView implements FxmlView<DescriptionEditViewModel> {

	@FXML
	public TextArea description;

	@InjectViewModel
	private DescriptionEditViewModel viewModel;


	public void initialize() {
		description.textProperty().bindBidirectional(viewModel.descriptionProperty());
	}


	public void save() {
		viewModel.save();
	}
}
