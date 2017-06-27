package de.saxsys.mvvmfx.utils.newitemlist;

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

public class ItemList<T, K> implements ViewItemList<K>{

	private Function<T, K> identifierFunction;
	private Function<T, String> labelFunction = Object::toString; // default to "toString"

	private ObjectProperty<T> selectedItem = new SimpleObjectProperty<T>();

	private ListTransformation<T, K> listTransformation;

	private String noSelectionPlaceHolder;
	private K noSelectionKey;

	public ItemList(Function<T, K> identifierFunction) {
		this.identifierFunction = Objects.requireNonNull(identifierFunction);
		listTransformation = new ListTransformation<>(FXCollections.observableArrayList(), identifierFunction);

		listTransformation.getModelList().addListener((ListChangeListener<T>) c -> {
            while(c.next()){
                if(c.wasRemoved()) {
                    if(c.getList().isEmpty()) {
                        selectedItem.setValue(null);
                    }
                }
            }
        });
	}

	public void replaceModelItems(Collection<? extends T> items) {
	    Objects.requireNonNull(items);
        T oldSelectedElement = selectedItem.get();

        listTransformation.getModelList().clear();
        listTransformation.getModelList().addAll(items);

        if(listTransformation.getModelList().contains(oldSelectedElement)) {
            selectedItem.setValue(oldSelectedElement);
        }
	}

	public void setLabelFunction(Function<T, String> labelFunction) {
		this.labelFunction = Objects.requireNonNull(labelFunction);
	}


	/**
	 * This method can be used to enable the handling of "no selection" use case.
	 * This is useful for {@link ChoiceBox} and {@link ComboBox}.
	 *
	 *
	 *
	 * @param placeholder
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

	public ObservableList<T> getModelList(){
		return listTransformation.getModelList();
	}


	// for testing only
    ObservableList<K> getKeyList() {
        return listTransformation.getTargetList();
    }

	public ObjectProperty<T> selectedItemProperty() {
		return selectedItem;
	}

	public T getSelectedItem() {
		return selectedItem.get();
	}

	public void setSelectedItem(T item) {
	    this.selectedItem.setValue(item);
    }


	private Optional<T> getModelByKey(K key) {
		Objects.requireNonNull(key);
		return listTransformation.getModelList().stream()
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
				int indexOfNoSelectionKey = comboBox.getButtonCell().getListView().getItems().indexOf(noSelectionKey);
				if(indexOfNoSelectionKey != -1) {
					comboBox.getButtonCell().updateIndex(indexOfNoSelectionKey);
				}
			});
		}

		Runnable removeSelectionModelListener = initSelectionModelListener(comboBox.getSelectionModel());

		return () -> {
			removeSelectionModelListener.run();
			comboBox.setConverter(null);
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

	@Override
	public Runnable connect(ListView<K> listView) {
		listView.setItems(listTransformation.getTargetList());

		listView.setCellFactory(new Callback<ListView<K>, ListCell<K>>() {
			@Override
			public ListCell<K> call(ListView<K> param) {
				return new ListCell<K>(){
					@Override
					protected void updateItem(K item, boolean empty) {
						super.updateItem(item, empty);
						setGraphic(null);
						if(empty) {
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
}
