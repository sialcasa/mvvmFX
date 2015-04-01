package de.saxsys.mvvmfx.example.todomvc.ui;

import de.saxsys.mvvmfx.example.todomvc.model.TodoItem;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author manuel.mauky
 */
public class Helper {
	
	public static ObservableList<TodoItem> completedItems (ObservableList<TodoItem> allItems){
		ObservableList<TodoItem> completedItems = FXCollections.observableArrayList();


		final InvalidationListener listener = observable -> {
			final List<TodoItem> completed = allItems.stream()
					.filter(TodoItem::isCompleted)
					.collect(Collectors.toList());

			completedItems.clear();
			completedItems.addAll(completed);
		};

		allItems.addListener((ListChangeListener<TodoItem>) c -> {
			c.next();

			listener.invalidated(null);

			if (c.wasAdded()) {
				c.getAddedSubList()
						.forEach(item -> item.completedProperty().addListener(listener));
			}

			if (c.wasRemoved()) {
				c.getRemoved()
						.forEach(item -> item.completedProperty().removeListener(listener));
			}
		});
		
		return completedItems;
	}
	
}
