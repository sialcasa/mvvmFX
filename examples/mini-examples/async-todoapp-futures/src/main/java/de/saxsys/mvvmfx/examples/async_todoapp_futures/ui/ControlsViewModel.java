package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItem;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItemService;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControlsViewModel implements ViewModel {

    private StringProperty input = new SimpleStringProperty();

    private ReadOnlyBooleanWrapper addPossible = new ReadOnlyBooleanWrapper();
    private ReadOnlyBooleanWrapper deletePossible = new ReadOnlyBooleanWrapper();

    @InjectScope
    private TodoScope todoScope;

    private TodoItemService itemService;

    public ControlsViewModel(TodoItemService itemService) {
        this.itemService = itemService;
    }

    public void initialize() {
        deletePossible.bind(todoScope.selectedItemProperty().isNotNull());
        addPossible.bind(input.isNotNull().and(input.isNotEmpty()));
    }

    public StringProperty inputProperty() {
        return input;
    }

    public ReadOnlyBooleanProperty addPossibleProperty() {
        return addPossible.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty deletePossibleProperty() {
        return deletePossible.getReadOnlyProperty();
    }

    public void addItem() {
        final String inputValue = input.getValue();

        if(inputValue != null && !inputValue.trim().isEmpty()) {
            itemService.createItem(new TodoItem(input.get()));

            input.setValue("");
            todoScope.publish(TodoScope.UPDATE_MSG);
        }
    }

    public void removeItem() {
        if(deletePossible.get()) {
            itemService.deleteItem(todoScope.selectedItemProperty().get());

            todoScope.selectedItemProperty().setValue(null);
            todoScope.publish(TodoScope.UPDATE_MSG);
        }
    }
}