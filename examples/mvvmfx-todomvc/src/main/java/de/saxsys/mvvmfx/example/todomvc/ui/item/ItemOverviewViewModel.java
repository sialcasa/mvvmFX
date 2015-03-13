package de.saxsys.mvvmfx.example.todomvc.ui.item;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import org.fxmisc.easybind.EasyBind;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItem;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItemStore;

/**
 * @author manuel.mauky
 */
public class ItemOverviewViewModel implements ViewModel {
	
	private ListProperty<ItemViewModel> items = new SimpleListProperty<>();
	
	public ItemOverviewViewModel() {
		final ObservableList<TodoItem> todoItems = TodoItemStore.getInstance().getItems();
		
		items.setValue(EasyBind.map(todoItems, ItemViewModel::new));
	}
	
	public ObservableList<ItemViewModel> itemsProperty() {
		return items.getValue();
	}
}
