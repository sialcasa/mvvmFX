package de.saxsys.mvvmfx.contacts.ui.detail;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class DetailView implements FxmlView<DetailViewModel> {
	@FXML
	public Label nameLabel;
	
	
	@InjectViewModel
	private DetailViewModel viewModel;
	
	public void initialize(){
		nameLabel.textProperty().bind(viewModel.nameProperty());
	}
	
}
