package de.saxsys.mvvmfx.contacts.ui.menu;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MenuView implements FxmlView<MenuViewModel> {

	@FXML
	private MenuItem removeMenuItem;


	@InjectViewModel
	private MenuViewModel viewModel;
	
	
	public void initialize(){
		removeMenuItem.disableProperty().bind(viewModel.removeItemDisabledProperty());
	}
	
	
	@FXML
	public void close(){
		viewModel.closeAction();
	}

	@FXML
	public void remove() {
		viewModel.removeAction();
	}
}
