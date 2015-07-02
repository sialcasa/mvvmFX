package de.saxsys.mvvmfx.examples.todomvc.ui.additems;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.todomvc.model.TodoItem;
import de.saxsys.mvvmfx.examples.todomvc.model.TodoItemStore;

/**
 * @author manuel.mauky
 */
public class AddItemsViewModel implements ViewModel {
	
	
	private BooleanProperty allSelected = new SimpleBooleanProperty();
	private StringProperty newItemValue = new SimpleStringProperty("");
	
	private ReadOnlyBooleanWrapper allSelectedVisible = new ReadOnlyBooleanWrapper();
	
	public AddItemsViewModel() {
		allSelected.addListener((obs, oldV, newV) -> {
			TodoItemStore.getInstance().getItems()
					.forEach(item -> item.setCompleted(newV));
		});
		
		allSelectedVisible.bind(Bindings.isEmpty(TodoItemStore.getInstance().getItems()).not());
	}
	
	
	public void addItem() {
		final String newValue = newItemValue.get();
		if (newValue != null && !newValue.trim().isEmpty()) {
			TodoItemStore.getInstance().getItems().add(new TodoItem(newValue));
			newItemValue.set("");
		}
	}
	
	public StringProperty newItemValueProperty() {
		return newItemValue;
	}
	
	
	public BooleanProperty allSelectedProperty() {
		return allSelected;
	}
	
	public ReadOnlyBooleanProperty allSelectedVisibleProperty() {
		return allSelectedVisible;
	}
}
