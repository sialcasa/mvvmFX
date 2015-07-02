package de.saxsys.mvvmfx.examples.todomvc.ui.item;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.examples.todomvc.ui.FilterHelper;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.todomvc.model.TodoItem;
import de.saxsys.mvvmfx.examples.todomvc.model.TodoItemStore;
import de.saxsys.mvvmfx.utils.itemlist.ListTransformation;

/**
 * @author manuel.mauky
 */
public class ItemOverviewViewModel implements ViewModel {
	
	private ListProperty<ItemViewModel> items = new SimpleListProperty<>();
	
	private final ListTransformation<TodoItem, ItemViewModel> allItems;
	private final ObservableList<ItemViewModel> completedItems;
	private final ObservableList<ItemViewModel> activeItems;
	
	public ItemOverviewViewModel() {
		allItems = new ListTransformation<>(TodoItemStore.getInstance().getItems(), ItemViewModel::new);
		
		completedItems = FilterHelper.filter(allItems.getTargetList(), ItemViewModel::completedProperty);
		activeItems = FilterHelper.filterInverted(allItems.getTargetList(), ItemViewModel::completedProperty);
		
		showAllItems();
		
		
		final NotificationCenter notificationCenter = MvvmFX.getNotificationCenter();
		notificationCenter.subscribe("showAll", (key, payload) -> showAllItems());
		notificationCenter.subscribe("showActive", (key, payload) -> showActiveItems());
		notificationCenter.subscribe("showCompleted", (key, payload) -> showCompletedItems());
	}
	
	private void showAllItems() {
		items.set(allItems.getTargetList());
	}
	
	private void showCompletedItems() {
		items.setValue(completedItems);
	}
	
	private void showActiveItems() {
		items.setValue(activeItems);
	}
	
	
	public ObservableList<ItemViewModel> itemsProperty() {
		return items;
	}
}
