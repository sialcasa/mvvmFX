package de.saxsys.mvvmfx.examples.todomvc.ui;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author manuel.mauky
 */
public class FilterHelper {

	private FilterHelper() {}

	public static <T> ObservableList<T> filter(ObservableList<T> items,
			Function<T, ObservableBooleanValue> conditionExtractor) {

		return filterInternal(items, conditionExtractor, t -> conditionExtractor.apply(t).get());
	}

	public static <T> ObservableList<T> filterInverted(ObservableList<T> items,
			Function<T, ObservableBooleanValue> conditionExtractor) {

		return filterInternal(items, conditionExtractor, t -> !conditionExtractor.apply(t).get());
	}

	private static <T> ObservableList<T> filterInternal(ObservableList<T> items,
			Function<T, ObservableBooleanValue> conditionExtractor, final Predicate<T> predicate) {
		final ObservableList<T> filteredItems = FXCollections.observableArrayList();
		final InvalidationListener listener = observable -> {
			final List<T> completed = items.stream().filter(predicate).collect(Collectors.toList());

			filteredItems.clear();
			filteredItems.addAll(completed);
		};

		items.addListener((ListChangeListener<T>) c -> {
			c.next();

			listener.invalidated(null);

			if (c.wasAdded()) {
				c.getAddedSubList().forEach(item -> conditionExtractor.apply(item).addListener(listener));
			}

			if (c.wasRemoved()) {
				c.getRemoved().forEach(item -> conditionExtractor.apply(item).removeListener(listener));
			}
		});

		return filteredItems;
	}

}
