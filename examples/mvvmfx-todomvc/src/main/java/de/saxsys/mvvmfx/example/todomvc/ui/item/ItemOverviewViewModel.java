package de.saxsys.mvvmfx.example.todomvc.ui.item;

import de.saxsys.mvvmfx.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author manuel.mauky
 */
public class ItemOverviewViewModel implements ViewModel {
	
	
	private ObservableList<ItemViewModel> items = FXCollections.observableArrayList();
	
	public ObservableList<ItemViewModel> itemsProperty() {
		return items;
	}
}

