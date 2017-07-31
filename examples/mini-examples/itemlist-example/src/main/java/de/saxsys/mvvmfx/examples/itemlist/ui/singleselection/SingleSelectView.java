package de.saxsys.mvvmfx.examples.itemlist.ui.singleselection;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SingleSelectView implements FxmlView<SingleSelectViewModel> {

	@FXML
	public ComboBox<String> comboBox;

	@FXML
	public ListView<String> listView;

	@FXML
	public ChoiceBox<String> choiceBox;

	@FXML
	public Label favoriteLabel;

	@FXML
	public TextField newValue;

	@FXML
	public Button saveNewValue;

	@InjectViewModel
	private SingleSelectViewModel viewModel;

	public void initialize() {
		favoriteLabel.textProperty().bind(viewModel.favoriteLabelTextProperty());

		viewModel.itemList().connect(comboBox);

		viewModel.itemList().connect(listView);

		viewModel.itemList().connect(choiceBox);



		newValue.textProperty().bindBidirectional(viewModel.newValueInputProperty());

		BooleanBinding newValueDisabled = viewModel.newValueEnabledProperty().not();
		newValue.disableProperty().bind(newValueDisabled);
		saveNewValue.disableProperty().bind(newValueDisabled);

	}

	public void saveNewValue() {
		viewModel.saveNewValue();
	}

	public void clearSelection() {
		viewModel.clearSelection();
	}
}
