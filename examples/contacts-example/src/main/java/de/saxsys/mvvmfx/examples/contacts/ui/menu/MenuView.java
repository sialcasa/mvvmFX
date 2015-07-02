package de.saxsys.mvvmfx.examples.contacts.ui.menu;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class MenuView implements FxmlView<MenuViewModel> {
	
	@FXML
	private MenuItem removeMenuItem;
	
	@InjectViewModel
	private MenuViewModel viewModel;
	
	
	public void initialize() {
		removeMenuItem.disableProperty().bind(viewModel.removeItemDisabledProperty());
	}
	
	
	@FXML
	public void close() {
		viewModel.closeAction();
	}
	
	@FXML
	public void remove() {
		viewModel.removeAction();
	}
	
	@FXML
	public void about() {
		viewModel.aboutAction();
	}
}
