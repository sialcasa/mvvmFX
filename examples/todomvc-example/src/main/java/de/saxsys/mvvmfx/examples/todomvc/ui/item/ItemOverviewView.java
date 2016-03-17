package de.saxsys.mvvmfx.examples.todomvc.ui.item;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;

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
		items.setCellFactory(CachedViewModelCellFactory.createForFxmlView(ItemView.class));
	}

}
