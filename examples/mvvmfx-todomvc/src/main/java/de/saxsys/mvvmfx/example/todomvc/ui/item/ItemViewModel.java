package de.saxsys.mvvmfx.example.todomvc.ui.item;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.example.todomvc.model.TodoItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author manuel.mauky
 */
public class ItemViewModel implements ViewModel {
	
	private BooleanProperty completed = new SimpleBooleanProperty();
	
	private StringProperty content = new SimpleStringProperty();
	
	public ItemViewModel(TodoItem item){
		content.bindBidirectional(item.textProperty());
		completed.bindBidirectional(item.completedProperty());
	}
	
	public void delete(){
		
	}

	public StringProperty contentProperty(){
		return content;
	}
	
	public BooleanProperty completedProperty() {
		return completed;
	}


	@Override
	public String toString() {
		return content.get();
	}
}
