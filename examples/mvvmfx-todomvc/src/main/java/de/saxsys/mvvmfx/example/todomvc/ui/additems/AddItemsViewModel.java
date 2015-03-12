package de.saxsys.mvvmfx.example.todomvc.ui.additems;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItem;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItemStore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author manuel.mauky
 */
public class AddItemsViewModel implements ViewModel {
	
	
	private StringProperty newItemValue = new SimpleStringProperty("");
	
	
	public void addItem(){
		final String newValue = newItemValue.get();
		if(newValue != null && !newValue.trim().isEmpty()){
			TodoItemStore.getInstance().getItems().add(new TodoItem(newValue));
			newItemValue.set("");
		}
		
	}
	
	public StringProperty newItemValueProperty(){
		return newItemValue;
	}
}
