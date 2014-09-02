package de.saxsys.mvvmfx.contacts.ui.master;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class MasterView implements FxmlView<MasterViewModel> {
	
	@FXML
	private TableView<MasterTableViewModel> contactTable;
	
	@InjectViewModel
	private MasterViewModel viewModel;
	
	public void initialize(){
		contactTable.setItems(viewModel.contactList());
		
		viewModel.selectedTableRowProperty().bind(contactTable.getSelectionModel().selectedItemProperty());
	}
	
}
