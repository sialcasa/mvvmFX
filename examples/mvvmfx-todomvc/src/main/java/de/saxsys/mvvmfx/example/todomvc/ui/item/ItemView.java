package de.saxsys.mvvmfx.example.todomvc.ui.item;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.scene.layout.HBox;

/**
 * @author manuel.mauky
 */
public class ItemView implements FxmlView<ItemViewModel> {
	
	@FXML
	public CheckBox completed;
	@FXML
	public TextField contentInput;
	
	@FXML
	public HBox contentBox;
	
	@FXML
	public Label contentLabel;
	
	@FXML
	public Button deleteButton;
	
	@InjectViewModel
	private ItemViewModel viewModel;
	
	
	public void initialize() {
		completed.selectedProperty().bindBidirectional(viewModel.completedProperty());
		contentInput.textProperty().bindBidirectional(viewModel.contentProperty());
		contentInput.visibleProperty().bind(viewModel.editModeProperty());
		contentInput.setOnAction(event -> viewModel.editModeProperty().set(false));
		contentInput.focusedProperty().addListener((obs,oldV,newV) -> {
			if(!newV) {
				viewModel.editModeProperty().set(false);
			}
		});
		
		contentBox.visibleProperty().bind(viewModel.editModeProperty().not());
		
		contentLabel.textProperty().bind(viewModel.contentProperty());
		contentLabel.setOnMouseClicked(event -> {
			if(event.getClickCount() > 1){
				viewModel.editModeProperty().set(true);
				contentInput.requestFocus();
			}
		});
	}
	
	public void delete() {
		viewModel.delete();
	}
}
