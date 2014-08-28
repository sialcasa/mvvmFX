package de.saxsys.mvvmfx.contacts.ui.menu;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class MenuView implements FxmlView<MenuViewModel> {
	
	@InjectViewModel
	private MenuViewModel viewModel;
	
	@FXML
	public void close(){
		viewModel.closeAction();
	}
}
