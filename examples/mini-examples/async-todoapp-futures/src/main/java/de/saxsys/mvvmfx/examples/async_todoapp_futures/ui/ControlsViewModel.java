package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItem;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItemService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

import java.util.concurrent.CompletableFuture;

public class ControlsViewModel implements ViewModel {

    private StringProperty input = new SimpleStringProperty();

    private ReadOnlyBooleanWrapper addPossible = new ReadOnlyBooleanWrapper();
    private ReadOnlyBooleanWrapper deletePossible = new ReadOnlyBooleanWrapper();

    private BooleanProperty uiDisabled = new SimpleBooleanProperty(false);

    @InjectScope
    private TodoScope todoScope;

    private TodoItemService itemService;

    public ControlsViewModel(TodoItemService itemService) {
        this.itemService = itemService;
    }

    public void initialize() {
        deletePossible.bind(Bindings.and(todoScope.selectedItemProperty().isNotNull(), uiDisabled.not()));
        addPossible.bind(Bindings.and(
                Bindings.and(
                        input.isNotNull(),
                        input.isNotEmpty()),
                    uiDisabled.not()));
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
            if(!uiDisabled.get()) {
                CompletableFuture
                    .runAsync(() -> {
                        uiDisabled.setValue(true);
                        itemService.createItem(new TodoItem(input.get()));
                    })
                    .thenRun(() -> {
                        uiDisabled.setValue(false);
                        input.setValue("");
                        todoScope.publish(TodoScope.UPDATE_MSG);
                        todoScope.setError(null);
                    }).exceptionally(throwable -> {
                        uiDisabled.setValue(false);
                        todoScope.setError(throwable.getCause());
                        return null;
                    });
            }
        }
    }

    public void removeItem() {
        if(deletePossible.get() && !uiDisabled.get()) {
            CompletableFuture.runAsync(() -> {
                uiDisabled.setValue(true);
                itemService.deleteItem(todoScope.selectedItemProperty().get());
            }).thenRun(() -> {
                uiDisabled.setValue(false);
                todoScope.selectedItemProperty().setValue(null);
                todoScope.publish(TodoScope.UPDATE_MSG);
                todoScope.setError(null);
            }).exceptionally(throwable -> {
                uiDisabled.setValue(false);
                todoScope.setError(throwable.getCause());
                return null;
            });
        }
    }
}