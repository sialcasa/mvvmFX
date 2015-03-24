package de.saxsys.mvvmfx.example.todomvc.ui.item;

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
		final ObservableList<TodoItem> todoItems = TodoItemStore.getInstance().getItems();
		
		listTransformation = new ListTransformation<>(todoItems, ItemViewModel::new);
		items = listTransformation.getTargetList();
	}
	
	public ObservableList<ItemViewModel> itemsProperty() {
		return items;
	}
}
