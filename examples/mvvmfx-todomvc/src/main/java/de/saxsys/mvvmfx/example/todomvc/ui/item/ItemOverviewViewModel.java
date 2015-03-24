package de.saxsys.mvvmfx.example.todomvc.ui.item;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItem;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItemStore;
import de.saxsys.mvvmfx.utils.itemlist.ListTransformation;

/**
 * @author manuel.mauky
 */
public class ItemOverviewViewModel implements ViewModel {
	
	private ObservableList<ItemViewModel> items;
	
	final ListTransformation<TodoItem, ItemViewModel> listTransformation;
	
	public ItemOverviewViewModel() {
		listTransformation = new ListTransformation<>(TodoItemStore.getInstance().getItems(), ItemViewModel::new);
		items = listTransformation.getTargetList();
	}
	
	public ObservableList<ItemViewModel> itemsProperty() {
		return items;
	}
}
