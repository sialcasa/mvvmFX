package de.saxsys.mvvmfx.examples.todomvc.ui.item;

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
	
	public static final String STRIKETHROUGH_CSS_CLASS = "strikethrough";
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
	@FXML
	public HBox root;
	
	@InjectViewModel
	private ItemViewModel viewModel;
	
	
	public void initialize() {
		deleteButton.setVisible(false);
		root.setOnMouseEntered(event -> {
			deleteButton.setVisible(true);
		});
		
		root.setOnMouseExited(event -> {
			deleteButton.setVisible(false);
		});
		
		completed.selectedProperty().bindBidirectional(viewModel.completedProperty());
		
		contentInput.textProperty().bindBidirectional(viewModel.contentProperty());
		contentInput.visibleProperty().bind(viewModel.editModeProperty());
		contentInput.setOnAction(event -> viewModel.editModeProperty().set(false));
		contentInput.focusedProperty().addListener((obs, oldV, newV) -> {
			if (!newV) {
				viewModel.editModeProperty().set(false);
			}
		});
		
		contentBox.visibleProperty().bind(viewModel.editModeProperty().not());
		completed.visibleProperty().bind(viewModel.editModeProperty().not());
		
		contentLabel.textProperty().bind(viewModel.contentProperty());
		contentLabel.setOnMouseClicked(event -> {
			if (event.getClickCount() > 1) {
				viewModel.editModeProperty().set(true);
				contentInput.requestFocus();
			}
		});
		
		
		viewModel.textStrikeThrough().addListener((obs, oldV, newV) -> {
			if (newV) {
				contentLabel.getStyleClass().add(STRIKETHROUGH_CSS_CLASS);
			} else {
				contentLabel.getStyleClass().remove(STRIKETHROUGH_CSS_CLASS);
			}
		});
		
	}
	
	public void delete() {
		viewModel.delete();
	}
}
