package de.saxsys.mvvmfx.contacts.ui.detail;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class DetailPanelView implements FxmlView<DetailPanelViewModel>{
	
	@FXML
	public Button editButton;
	@FXML
	public Button removeButton;

	
	@InjectViewModel
	private DetailPanelViewModel viewModel;
	
	public void initialize(){
		removeButton.disableProperty().bind(viewModel.removeButtonEnabledProperty().not());


		AwesomeDude.setIcon(editButton, AwesomeIcon.EDIT);
		AwesomeDude.setIcon(removeButton, AwesomeIcon.TRASH_ALT);
	}
	
	@FXML
	public void edit(){
		viewModel.editAction();
	}
	
	@FXML
	public void remove() {
		viewModel.removeAction();
	}
}
