package de.saxsys.mvvmfx.example.todomvc.ui.controls;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItem;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItemStore;
import de.saxsys.mvvmfx.example.todomvc.ui.Helper;

/**
 * @author manuel.mauky
 */
public class ControlsViewModel implements ViewModel {
	
	private StringProperty itemsLeftLabelText = new SimpleStringProperty();
	
	
	public ControlsViewModel() {
		final ObservableList<TodoItem> items = TodoItemStore.getInstance().getItems();
		
		ObservableList<TodoItem> completedItems = Helper.completedItems(items);
		
		final IntegerBinding size = Bindings.size(completedItems);
		
		final StringBinding itemsLabel = Bindings.when(size.isEqualTo(1)).then("item").otherwise("items");
		itemsLeftLabelText.bind(Bindings.concat(size, " ", itemsLabel, " left"));
	}
	
	public StringProperty itemsLeftLabelTextProperty() {
		return itemsLeftLabelText;
	}
}
