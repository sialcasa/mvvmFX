package de.saxsys.mvvmfx.examples.itemlist;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ItemListExampleView implements FxmlView<ItemListExampleViewModel> {

	@FXML
	public ComboBox<String> comboBox;

	@FXML
	public ListView<String> listView;

	@FXML
	public ChoiceBox<String> choiceBox;

	@FXML
	public Label favoriteLabel;

	@InjectViewModel
	private ItemListExampleViewModel viewModel;

	public void initialize() {
		favoriteLabel.textProperty().bind(viewModel.favoriteLabelTextProperty());

		viewModel.itemList().connect(comboBox);

		viewModel.itemList().connect(listView);

		viewModel.itemList().connect(choiceBox);
	}
}
