package de.saxsys.mvvmfx.utils.items;

import de.saxsys.mvvmfx.utils.itemlist.ListTransformation;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;


/**
 * This class is used to handle UI controls that have the concept of "items"
 * (like {@link ListView}, {@link ChoiceBox} and {@link ComboBox}) in a MVVM compatible way.
 * In pure JavaFX you typically either use String or a model class like "Person"
 * as generic type for the items in a {@link ListView}. However, both approaches have downsides:
 * <p/>
 * Using a model class violates the MVVM principle that the View may not have any dependencies to Model classes.
 * Using a String representation of the model object is compatible with MVVM
 * but makes it harder to implement features like selection because the
 * {@link SelectionModel#getSelectedItem()} of the ListView will also be a String representation which often
 * won't be easy to map back to the actual model object.
 * <p/>
 * Another problem of handling UI controls like {@link ListView} in MVVM is that the {@link SelectionModel#selectedItemProperty()}
 * is read-only. For this reason you can't simply bind the ListView bidirectional
 * to a "selectedItem" property in your ViewModel. Instead you would need to create listeners in both directions.
 * <p/>
 * The purpose of this class is to provide an easy to use and MVVM compatible solution for these problem.
 * <p/>
 * The idea is that in your ViewModel you have an {@link ItemList} instance and use it to manage the
 * model items and selection. The class has two generic type arguments:
 * The first for the actual model type and the second for the type of the identifier of the model type.
 * Typically this will be {@link Long} for a database ID or {@link String}  or {@link java.util.UUID}.
 * In your View you will have a {@link ListView} (or another supported UI Control) with the
 * generic type of the ID instead of the actual model (It is even possible to leave the generic type of the ListView
 * empty if you like to completely hide the type of the Model class to the View).
 * All the handling of items and selection is done in the ViewModel.
 * Your ViewModel then has to provide the {@link ItemList} to your View.
 * <p/>
 * In the View you just {@link #connect(ListView)} your ListView to the {@link ItemList} and you are done in your View.
 * To prevent the View from accidentally changing the ItemList configuration your getter method that provides
 * the {@link ItemList} should have the return type of {@link ViewItemList} instead of {@link ItemList}.
 * {@link ViewItemList} is an interface implemented by this class that defines only the methods needed by the View.
 * <p/>
 * The {@link ItemList} can be connected to several UI controls, not just the {@link ListView}.
 * The supported UI-Controls can be seen in the {@link ViewItemList} interface.
 * It is also possible to connect multiple ui controls (of different types) to the same instance of {@link ItemList}.
 * This can be seen in the example app at the [github repository](https://github.com/sialcasa/mvvmFX/tree/develop/examples/mini-examples/itemlist-example).
 * <p/>
 *
 * @param <T> the type of the model class
 * @param <K> the type of the identifier of the model class
 */
public class ItemList<T, K> implements ViewItemList<K>{

	private Function<T, K> identifierFunction;
	private Function<T, String> labelFunction = Object::toString; // default to "toString"

	private ObjectProperty<T> selectedItem = new SimpleObjectProperty<T>();

	private ListTransformation<T, K> listTransformation;

	private String noSelectionPlaceHolder;
	private K noSelectionKey;

	// can be used to get an observable list of the label values.
	// this list is created lazily only when it is used by the developer.
	private ListTransformation<K, String> labelList;

	/**
	 * The constructor takes an identifierFunction as argument. This function has to return the ID for
	 * a given model instance. Typically this can be a method reference to the getter of the ID in the model class.
	 * <p/>
	 * Example:
	 * <pre>
	 *     new ItemList{@code <Person, Long>}(Person::getId);
	 * </pre>
	 *
	 * @param identifierFunction a function that returns the identifier for a given model object.
	 */
	public ItemList(Function<T, K> identifierFunction) {
		this.identifierFunction = Objects.requireNonNull(identifierFunction);
		listTransformation = new ListTransformation<>(FXCollections.observableArrayList(), identifierFunction);

		listTransformation.getSourceList().addListener((ListChangeListener<T>) c -> {
            while(c.next()){
                if(c.wasRemoved()) {
                    if(c.getList().isEmpty()) {
                        selectedItem.setValue(null);
                    }
                }
            }
        });
	}

	/**
	 * This method can be used to replace the items of the {@link ItemList} with the new items passed as argument.
	 * If an element was selected before the replacement this selection will be preserved after the replacement if the
	 * selected item is still contained in the new item collection.
	 *
	 * @param items a collection of items that will be replaced.
	 */
	public void replaceModelItems(Collection<? extends T> items) {
	    Objects.requireNonNull(items);
        T oldSelectedElement = selectedItem.get();

		listTransformation.getSourceList().clear();
		listTransformation.getSourceList().addAll(items);

        if(listTransformation.getSourceList().contains(oldSelectedElement)) {
            selectedItem.setValue(oldSelectedElement);
        }
	}

	/**
	 * Defines a function that is used to calculate the label text on ui controls for the given model item.
	 * <p/>
	 * By default {@link Object#toString()} is used.
 	 *
	 * @param labelFunction a function that takes an object instance as argument and returns a string representation.
	 */
	public void setLabelFunction(Function<T, String> labelFunction) {
		this.labelFunction = Objects.requireNonNull(labelFunction);
	}


	/**
	 * This method can be used to enable the handling of "no selection" use cases.
	 * This is especially useful for {@link ChoiceBox} and {@link ComboBox} but not for {@link ListView}s.
	 * <p/>
	 * You use {@link ChoiceBox} or {@link ComboBox} to let the user select a specific
	 * value out of a range of values. However, sometimes it has to be possible for the user to tell the application that "nothing is selected"
	 * or to remove the selection of the previous value.
	 * Because when using the {@link ItemList} class the list of possible values is determined by a list of model items we would need
	 * to have a representation of "nothing" in this list. Using <code>null</code> for this usecase is sometimes not desired in the model list
	 * and also produces some problems in the JavaFX Controls handling. <p/>
	 * To use this feature you have to provide two arguments:<p/>
	 * First a value of the type of the identifier of the model classes that can be used to represent "nothing selected".
	 * This key may not be used for "real" model objects in the list.
	 * For example when your model class has IDs of type {@link Long} a possible value may be <code>-1</code> if you can make sure that this value isn't used anywere else.
	 * If you model uses {@link java.util.UUID} or a {@link String} representation of UUIDs then simply create a new UUID for this value.
	 * <p/>
	 * The second argument is the String value that should be shown to the user. This could be something like "no selection" or "---".
	 * At the moment, no I18N is supported here. If 118N is needed you can inject the {@link java.util.ResourceBundle} into your
	 * ViewModel and resolve the translation in your ViewModel.
	 *
	 * @param noSelectionKey an ID that identifies the case when nothing is selected.
	 * @param placeholder a string label that is used in the ui control when nothing is selected.
	 */
	public void enableNoSelection(K noSelectionKey, String placeholder) {
		this.noSelectionKey = Objects.requireNonNull(noSelectionKey);
		this.noSelectionPlaceHolder = Objects.requireNonNull(placeholder);
	}


	/**
	 * This method is used to clear the selection.
	 * It is a shortcut to <code>itemList.selectedItemProperty().setValue(null);</code>.
	 */
	public void clearSelection() {
		selectedItem.setValue(null);
	}

	/**
	 * Returns the observable list of model items.
	 * It is possible to add your model items to this {@link ItemList} by using this method but
	 * in most use-cases it's better to use the {@link #replaceModelItems(Collection)} method instead because
	 * it also handles the (re-)selection of items.<p/>
	 *
	 * @return the model list
	 */
	public ObservableList<T> getSourceList(){
		return listTransformation.getSourceList();
	}

	/**
	 * Returns an observable list of IDs as defined by the identifierFunction in {@link #ItemList(Function)}.
	 * For typical use-cases you won't need this method but it may be useful for testing purposes.
	 *
	 * @return an observable list of IDs
	 */
    public ObservableList<K> getKeyList() {
        return listTransformation.getTargetList();
    }

	/**
	 * Returns an observable list of String labels that are used in UI controls.
	 * For typical use-cases you won't need this method but it may be useful for testing purposes.
	 *
	 * @return an observable list of String labels.
	 */
	public ObservableList<String> getLabelList() {
		if(labelList == null) {
			labelList = new ListTransformation<>(listTransformation.getTargetList(),
					key -> keyItemConverter.toString(key));
		}

		return labelList.getTargetList();
	}

	/**
	 * Returns an object property of the currently selected item.
	 * In contrast to the standard JavaFX {@link SelectionModel#selectedIndexProperty()} this property can be used
	 * to set the selected item and it can be used in bidirectional bindings.
	 * <p/>
	 * This property may contain <code>null</code> as value when nothing is selected.
	 * You can also set this property to <code>null</code> in your code to remove the selection.
	 *
	 * @return the selected item as property. the property may contain <code>null</code> if nothing is selected.
	 */
	public ObjectProperty<T> selectedItemProperty() {
		return selectedItem;
	}

	/**
	 * See {@link #selectedItemProperty()}.
	 * @return the selected item or <code>null</code> if nothing is selected.
	 */
	public T getSelectedItem() {
		return selectedItem.get();
	}

	/**
	 * See {@link #selectedItemProperty()}.
	 * @param item the item that should be selected or <code>null</code> to remove the selection.
	 */
	public void setSelectedItem(T item) {
	    this.selectedItem.setValue(item);
    }

	/**
	 * For a given ID this method returns an {@link Optional} containing the model item with this ID if it is contained
	 * in this {@link ItemList}.<p/>
	 * This can be useful in more complex use-cases when you need to define your own <i>CellFactory</i> for a {@link ListView}.
	 * By default the {@link ItemList} defines it's own cellFactory to render the a String label (See {@link #setLabelFunction(Function)}).
	 * This default cellFactory can be replaced by your own cellFactory.
	 * However, in your cellFactory you won't have direct access to the actual model item but instead only to the ID because
	 * the {@link ItemList} only uses the ID of model Items as actual items in the {@link ListView}.
	 * But to create a meaningful cellFactory in most cases you will need the actual model item.
	 * For this purpose you can use this method.<p/>
	 * As the cellFactory is defined in the View/CodeBehind you need to make this method accessible from the View by either passing
	 * the {@link ItemList} to the View instead of the {@link ViewItemList}(discouraged) or by proxying the {@link #getModelByKey(Object)}
	 * method in the ViewModel so that it can be accessed by the View. <p/>
	 *
	 * <p/>
	 * If your list items are mvvmFX based Views too you may also have a look at
	 * {@link de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory} which is targeted especially for this use case.
	 * However, it doesn't support extensive selection handling (yet) like {@link ItemList} does.
	 *
	 * @param key the ID for which a model object is looked up.
	 * @return an optional containing the model object if it was found.
	 */
	public Optional<T> getModelByKey(K key) {
		Objects.requireNonNull(key);
		return listTransformation.getSourceList().stream()
				.filter(modelItem -> key.equals(identifierFunction.apply(modelItem)))
				.findFirst();
	}

	private StringConverter<K> keyItemConverter = new StringConverter<K>() {
		@Override
		public String toString(K item) {
			if(item == null) {
				if(noSelectionPlaceHolder == null ){
					return null;
				} else {
					return noSelectionPlaceHolder;
				}
			} else {
				if(noSelectionKey != null && noSelectionKey.equals(item)) {
					return noSelectionPlaceHolder;
				} else {
					Optional<T> modelOptional = getModelByKey(item);

					return modelOptional.map(labelFunction).orElse(null);
				}
			}
		}

		@Override
		public K fromString(String string) {
			return null;
		}
	};

	/**
	 * If "no selection" is enabled we need to add a dummy entry
	 * to the item list that is used by comboBox/choiceBox.
	 * This dummy entry represents "nothing selected" and can be choosen by the user
	 * without being a "real" entry in the model list.
	 */
	@SuppressWarnings("unchecked")
	private ObservableList<K> createComboBoxItemList() {
		if(noSelectionKey == null) {
			return listTransformation.getTargetList();
		} else {
			return FXCollections.concat(
					FXCollections.singletonObservableList(noSelectionKey),
					listTransformation.getTargetList());
		}
	}

	@Override
	public Runnable connect(ComboBox<K> comboBox) {
		comboBox.setItems(createComboBoxItemList());


		// we need to "copy" the selected value to the combobox to keep it sync with the ItemList.
		if(selectedItem.get() == null) {
			comboBox.setValue(null);
			comboBox.getSelectionModel().select(null);
		} else {
			K selectedKey = identifierFunction.apply(selectedItem.get());

			comboBox.setValue(selectedKey);
			comboBox.getSelectionModel().select(selectedKey);
		}


		if(noSelectionKey != null) {
			// We need to patch the buttonCell of the ComboBox (the value that is visible on the combobox itself)
			// so that it shows the "no selection" placeholder when "no selection" is enabled.
			comboBox.setButtonCell(new ListCell<K>() {
				@Override
				protected void updateItem(K item, boolean empty) {
					super.updateItem(item, empty);

					// the converter takes care for selecting the "no selection" placeholder if needed.
					setText(keyItemConverter.toString(item));
				}
			});
		}

		comboBox.setConverter(keyItemConverter);

		// if nothing is selected atm and "no selection" is enabled, we need to update the buttonCell
		// due to a bug (?) in JavaFX. Otherwise, the combobox button would still be empty.
		if(selectedItem.get() == null && noSelectionKey != null) {
			Platform.runLater(() -> {
				if (comboBox.getButtonCell().getListView() != null) {
					int indexOfNoSelectionKey = comboBox.getButtonCell().getListView().getItems()
							.indexOf(noSelectionKey);
					if (indexOfNoSelectionKey != -1) {
						comboBox.getButtonCell().updateIndex(indexOfNoSelectionKey);
					}
				}
			});
		}

		Runnable removeSelectionModelListener = initSelectionModelListener(comboBox.getSelectionModel());

		return () -> {
			removeSelectionModelListener.run();
			comboBox.setConverter(null);
		};
	}

	@Override
	public Runnable connect(ListView<K> listView) {
		listView.setItems(listTransformation.getTargetList());

		if(listView.getCellFactory() == null) {
			listView.setCellFactory(new Callback<ListView<K>, ListCell<K>>() {
				@Override
				public ListCell<K> call(ListView<K> param) {
					return new ListCell<K>(){
						@Override
						protected void updateItem(K item, boolean empty) {
							super.updateItem(item, empty);
							setGraphic(null);
							if(empty || item == null) {
								setText(null);
							} else {
								Optional<T> modelOptional = getModelByKey(item);

								String text = modelOptional.map(labelFunction).orElse(null);
								setText(text);
							}
						}
					};
				}
			});
		}


		if(selectedItem.get() == null) {
			listView.getSelectionModel().select(null);
		} else {
			K selectedKey = identifierFunction.apply(selectedItem.get());
			listView.getSelectionModel().select(selectedKey);
		}

		Runnable removeSelectionModelListener = initSelectionModelListener(listView.getSelectionModel());


		// in addition to the other listeners we need a special listener on the selectionModel for ListViews.
		// the reason is that the ListView doesn't show a "no selection" placeholder when "no selection" is enabled.
		// instead for ListView we have to use "clearSelection" instead.
		ChangeListener<K> clearSelectionListener = (observable, oldValue, newValue) -> {
			if (noSelectionKey != null && noSelectionKey.equals(newValue)) {
				listView.getSelectionModel().clearSelection();
			}
		};
		listView.getSelectionModel().selectedItemProperty().addListener(clearSelectionListener);

		return () -> {
			removeSelectionModelListener.run();
			listView.setCellFactory(null);
			listView.getSelectionModel().selectedItemProperty().removeListener(clearSelectionListener);
		};
	}

	@Override
	public Runnable connect(ChoiceBox<K> choiceBox) {
		choiceBox.setItems(createComboBoxItemList());

		choiceBox.setConverter(keyItemConverter);

		if (selectedItem.get() == null) {
			if(noSelectionKey != null) {
				choiceBox.setValue(noSelectionKey);
			}
		} else {
			K selectedKey = identifierFunction.apply(selectedItem.get());
			choiceBox.setValue(selectedKey);
		}

		Runnable removeSelectionModelListener = initSelectionModelListener(choiceBox.getSelectionModel());

		return () -> {
			removeSelectionModelListener.run();
			choiceBox.setConverter(null);
		};
	}

	private Runnable initSelectionModelListener(SelectionModel<K> selectionModel) {
		final ChangeListener<K> selectionModelListener = (observable, oldValue, newValue) -> {
			if (newValue == null) {
				selectedItem.setValue(null);
			} else {
				if(noSelectionKey != null && newValue.equals(noSelectionKey)) {
					selectedItem.setValue(null);
				} else {
					Optional<T> selectedItemOptional = getModelByKey(newValue);

					selectedItemOptional.ifPresent(selectedItem::setValue);
				}
			}
		};
		selectionModel.selectedItemProperty().addListener(selectionModelListener);

		ChangeListener<T> selectedItemListener = (observable, oldValue, newValue) -> {
			if (newValue == null) {
				if(noSelectionKey != null){
					selectionModel.select(noSelectionKey);
				} else {
					selectionModel.clearSelection();
				}
			} else {
				K key = identifierFunction.apply(newValue);

				selectionModel.select(key);
			}
		};
		selectedItem.addListener(selectedItemListener);


		return () -> {
			selectionModel.selectedItemProperty().removeListener(selectionModelListener);
			selectedItem.removeListener(selectedItemListener);
		};
	}

}
