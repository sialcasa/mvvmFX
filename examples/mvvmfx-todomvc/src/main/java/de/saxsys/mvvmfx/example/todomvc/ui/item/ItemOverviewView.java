package de.saxsys.mvvmfx.example.todomvc.ui.item;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.ViewListCellFactory;

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
		
		final ViewListCellFactory<ItemViewModel> cellFactory = viewModel -> FluentViewLoader.fxmlView(ItemView.class)
				.viewModel(viewModel).load();
		items.setCellFactory(cellFactory);
	}
	
}
