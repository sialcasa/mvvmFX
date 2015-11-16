package de.saxsys.mvvmfx.examples.contacts.ui.master;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;


public class MasterView implements FxmlView<MasterViewModel> {
	
	@FXML
	private TableView<MasterTableViewModel> contactTable;
	
	@InjectViewModel
	private MasterViewModel viewModel;
	
	public void initialize() {
		contactTable.setItems(viewModel.getContactList());
		
		viewModel.selectedTableRowProperty().bind(contactTable.getSelectionModel().selectedItemProperty());
		
		// When the selectedTableRowProperty changes in the viewModel we need to update the table
		viewModel.setOnSelect(vm -> contactTable.getSelectionModel().select(vm));
	}
	
}
