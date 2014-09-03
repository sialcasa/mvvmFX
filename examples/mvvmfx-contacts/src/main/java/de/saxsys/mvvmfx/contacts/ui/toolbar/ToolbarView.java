package de.saxsys.mvvmfx.contacts.ui.toolbar;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ToolbarView implements FxmlView<ToolbarViewModel> {

	@FXML
	public Button addNewContactButton;
	
	@InjectViewModel
	private ToolbarViewModel viewModel;
	
	public void initialize(){
		AwesomeDude.setIcon(addNewContactButton, AwesomeIcon.PLUS);
	}
	
	@FXML
	public void addNewContact(){
		viewModel.addNewContactAction();
	}
}
