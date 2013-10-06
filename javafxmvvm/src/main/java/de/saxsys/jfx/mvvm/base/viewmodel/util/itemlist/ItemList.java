package de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.util.StringConverter;

/**
 * Element that you can use in a View Model to transform any list to a string
 * representation which can be bound to UI Elements like {@link ListView}.
 * <b>You should only expose the {@link #stringListProperty()} to the view,
 * otherwise you create a visibility of the view to the model. Create something
 * like this in your View Model:
 * 
 * <code>
 * public SelectableStringList stringListProperty(){
 * 		return itemList.stringListProperty();
 * }
 * </code>
 * 
 * </b> You have to provide a {@link StringConverter} to define how to convert a
 * string. In addition you have properties which represents the actual selection
 * state of a list.
 * 
 * @author sialcasa
 * 
 * @param <ListType>
 *            type of the list elements which should be transformed to a string
 *            list
 */
public class ItemList<ListType> {
	// Converter
	private final StringConverter<ListType> stringConverter;

	// The two lists - List which was provided and the String representation of
	// the list
	private ReadOnlyListWrapper<String> stringList = new ReadOnlyListWrapper<>(
			FXCollections.<String> observableArrayList());
	private ListProperty<ListType> itemList = new SimpleListProperty<>();

	/**
	 * Creates a {@link ItemList} by a given list of items and a string
	 * converter.
	 * 
	 * @param itemList
	 *            which should be transformed for the UI
	 * @param stringConverter
	 *            which is used for transformation
	 */
	public ItemList(ObservableList<ListType> itemList,
			final StringConverter<ListType> stringConverter) {
		this.stringConverter = stringConverter;
		createListEvents();
		this.itemListProperty().set(itemList);
	}

	// If the list changed we want the recreate the string
	private void createListEvents() {
		// TODO Remove Listener from itemList anywhen - prevent memory leak
		itemListProperty().addListener(new ListChangeListener<ListType>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends ListType> listEvent) {
				stringList.clear();
				for (ListType item : listEvent.getList()) {
					stringList.add(ItemList.this.stringConverter.toString(item));
				}
			}
		});
	}

	/**
	 * @return List of elements which should be transformed.
	 */
	public ListProperty<ListType> itemListProperty() {
		return itemList;
	}

	/**
	 * @return String representation of {@link #itemListProperty()}.
	 */
	public ReadOnlyListProperty<String> stringListProperty() {
		return stringList.getReadOnlyProperty();
	}

}
