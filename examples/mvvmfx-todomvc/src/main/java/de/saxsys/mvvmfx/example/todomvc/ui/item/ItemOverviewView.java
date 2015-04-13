package de.saxsys.mvvmfx.example.todomvc.ui.item;

import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

/**
 * @author manuel.mauky
 */
public class ItemOverviewView implements FxmlView<ItemOverviewViewModel> {
	
	@FXML
	public ListView<ItemViewModel> items;
	
	@InjectViewModel
	private ItemOverviewViewModel viewModel;
	
	public void initialize() {
		items.setItems(viewModel.itemsProperty());
		
		items.setCellFactory(CachedViewModelCellFactory.create(
				vm -> FluentViewLoader.fxmlView(ItemView.class).viewModel(vm).load()));
	}
	
}
