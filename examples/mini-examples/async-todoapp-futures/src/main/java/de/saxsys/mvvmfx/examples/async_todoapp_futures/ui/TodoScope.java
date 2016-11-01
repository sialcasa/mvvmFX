package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class TodoScope implements Scope {

    public static final String UPDATE_MSG = "TodoScope.UPDATE_MSG";

    private ObjectProperty<TodoItem> selectedItem = new SimpleObjectProperty<>();

    public ObjectProperty<TodoItem> selectedItemProperty() {
        return selectedItem;
    }
}
