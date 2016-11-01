package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItem;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;

public class TodoScope implements Scope {

    public static final String UPDATE_MSG = "TodoScope.UPDATE_MSG";

    private ObjectProperty<Throwable> error = new SimpleObjectProperty<>();
    private ReadOnlyObjectWrapper<TodoItem> selectedItem = new ReadOnlyObjectWrapper<>();

    public ObjectProperty<TodoItem> selectedItemProperty() {
        return selectedItem;
    }

    public void setError(Throwable throwable) {
        Platform.runLater(() -> error.setValue(throwable));
    }

    public ReadOnlyObjectProperty<Throwable> errorProperty() {
        return error;
    }
}
