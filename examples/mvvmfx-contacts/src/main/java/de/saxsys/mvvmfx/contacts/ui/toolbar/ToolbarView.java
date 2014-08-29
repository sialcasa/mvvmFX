package de.saxsys.mvvmfx.contacts.ui.toolbar;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class ToolbarView implements FxmlView<ToolbarViewModel> {
	
	@InjectViewModel
	private ToolbarViewModel viewModel;
	
	@FXML
	public void addNewContact(){
		viewModel.addNewContactAction();
	}
}
