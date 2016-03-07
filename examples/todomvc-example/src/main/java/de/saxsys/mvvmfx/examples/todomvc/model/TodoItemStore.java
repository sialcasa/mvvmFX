package de.saxsys.mvvmfx.examples.todomvc.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author manuel.mauky
 */
public class TodoItemStore {

	private static final TodoItemStore SINGLETON = new TodoItemStore();

	private final ObservableList<TodoItem> items = FXCollections.observableArrayList();

	private TodoItemStore() {
	}

	public static TodoItemStore getInstance() {
		return SINGLETON;
	}

	public ObservableList<TodoItem> getItems() {
		return items;
	}

}
