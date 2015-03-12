package de.saxsys.mvvmfx.example.todomvc.ui.item;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * @author manuel.mauky
 */
public class ItemView implements FxmlView<ItemViewModel> {
	
	@FXML
	public CheckBox completed;
	@FXML
	public TextField content;
	@FXML
	public Button deleteButton;

	@InjectViewModel
	private ItemViewModel viewModel;
	
	
	public void initialize(){
		completed.selectedProperty().bindBidirectional(viewModel.completedProperty());
		content.textProperty().bindBidirectional(viewModel.contentProperty());
	}
	
	public void delete() {
		viewModel.delete();
	}
}
