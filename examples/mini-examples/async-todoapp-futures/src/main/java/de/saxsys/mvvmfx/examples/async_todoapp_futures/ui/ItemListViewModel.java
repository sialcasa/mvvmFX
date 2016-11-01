package de.saxsys.mvvmfx.examples.async_todoapp_futures.ui;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItem;
import de.saxsys.mvvmfx.examples.async_todoapp_futures.model.TodoItemService;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class ItemListViewModel implements ViewModel {

    private ObservableList<TodoItem> items = FXCollections.observableArrayList();
    private ObjectProperty<TodoItem> selectedItem = new SimpleObjectProperty<>();

    @InjectScope
    private TodoScope todoScope;

    private TodoItemService itemService;

    public ItemListViewModel(TodoItemService itemService) {
        this.itemService = itemService;
    }

    public void initialize() {
        todoScope.subscribe(TodoScope.UPDATE_MSG, (k,v) -> update());
        update();

        todoScope.selectedItemProperty().bindBidirectional(selectedItem);
    }

    private void update() {
                CompletableFuture.supplyAsync(() -> {
                    return itemService.getAllItems();
                }).thenAccept(allItems -> {
                    Collections.reverse(allItems);
                    Platform.runLater(() -> items.setAll(allItems));
                    todoScope.setError(null);
                }).exceptionally(throwable -> {
                    todoScope.setError(throwable.getCause());
                    return null;
                });
    }

    public ObservableList<TodoItem> itemsProperty() {
        return items;
    }

    public ObjectProperty<TodoItem> selectedItemProperty() {
        return selectedItem;
    }

}
