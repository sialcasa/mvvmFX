package de.saxsys.mvvmfx.examples.todomvc.ui.item;

import de.saxsys.mvvmfx.examples.todomvc.model.TodoItemStore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.todomvc.model.TodoItem;

/**
 * @author manuel.mauky
 */
public class ItemViewModel implements ViewModel {

    private final BooleanProperty completed = new SimpleBooleanProperty();
    private final BooleanProperty editMode = new SimpleBooleanProperty();

    private final StringProperty content = new SimpleStringProperty();

    private TodoItem item;

    public ItemViewModel(TodoItem item) {
        this.item = item;
        content.bindBidirectional(item.textProperty());
        completed.bindBidirectional(item.completedProperty());
    }

    public void delete() {
        TodoItemStore.getInstance().getItems().remove(item);
    }

    public StringProperty contentProperty() {
        return content;
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public BooleanProperty editModeProperty() {
        return editMode;
    }

    public ReadOnlyBooleanProperty textStrikeThrough() {
        return completed;
    }
    
}
