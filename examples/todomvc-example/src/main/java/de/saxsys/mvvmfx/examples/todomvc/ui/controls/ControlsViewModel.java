package de.saxsys.mvvmfx.examples.todomvc.ui.controls;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.todomvc.model.TodoItem;
import de.saxsys.mvvmfx.examples.todomvc.model.TodoItemStore;
import de.saxsys.mvvmfx.examples.todomvc.ui.FilterHelper;

/**
 * @author manuel.mauky
 */
public class ControlsViewModel implements ViewModel {

    private final StringProperty itemsLeftLabelText = new SimpleStringProperty();

    private final NotificationCenter notificationCenter = MvvmFX.getNotificationCenter();

    public ControlsViewModel() {
        final ObservableList<TodoItem> items = TodoItemStore.getInstance().getItems();

        ObservableList<TodoItem> completedItems = FilterHelper.filterInverted(items, TodoItem::completedProperty);

        final IntegerBinding size = Bindings.size(completedItems);

        final StringBinding itemsLabel = Bindings.when(size.isEqualTo(1)).then("item").otherwise("items");
        itemsLeftLabelText.bind(Bindings.concat(size, " ", itemsLabel, " left"));
    }

    public StringProperty itemsLeftLabelTextProperty() {
        return itemsLeftLabelText;
    }

    public void all() {
        notificationCenter.publish("showAll");
    }

    public void active() {
        notificationCenter.publish("showActive");
    }

    public void completed() {
        notificationCenter.publish("showCompleted");
    }
    
}
