package de.saxsys.mvvmfx.utils.newitemlist;

import de.saxsys.mvvmfx.utils.itemlist.ListTransformation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	public ItemList(Function<T, K> identifierFunction) {
		this.identifierFunction = identifierFunction;
		listTransformation = new ListTransformation<>(FXCollections.observableArrayList(), identifierFunction);

		listTransformation.getModelList().addListener(new ListChangeListener<T>() {
			@Override
			public void onChanged(Change<? extends T> c) {
				while(c.next()){
					if(c.wasRemoved()) {
						if(c.getList().isEmpty()) {
							selectedItem.setValue(null);
						}
					}
				}
			}
		});
	}

	public void replaceModelItems(Collection<T> items) {
		replaceModelItems(items, true);
	}

	public void replaceModelItems(Collection<T> items, boolean keepSelection) {
		if(keepSelection) {
			T oldSelectedElement = selectedItem.get();

			listTransformation.getModelList().clear();
			listTransformation.getModelList().addAll(items);

			if(listTransformation.getModelList().contains(oldSelectedElement)) {
				selectedItem.setValue(oldSelectedElement);
			}
		} else {
			listTransformation.getModelList().clear();
			listTransformation.getModelList().addAll(items);
		}

	}

	public void setLabelFunction(Function<T, String> labelFunction) {
		Objects.requireNonNull(labelFunction);
		this.labelFunction = labelFunction;
	}

	public ObservableList<T> getModelList(){
		return listTransformation.getModelList();
	}

	public ObjectProperty<T> selectedItemProperty() {
		return selectedItem;
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
				return null;
			} else {
				Optional<T> modelOptional = getModelByKey(item);

				return modelOptional.map(labelFunction).orElse(null);
			}
		}

		@Override
		public K fromString(String string) {
			return null;
		}
	};

	@Override
	public Runnable connect(ComboBox<K> comboBox) {
		comboBox.setItems(listTransformation.getTargetList());

		comboBox.setConverter(keyItemConverter);

		Runnable removeSelectionModelListener = initSelectionModelListener(comboBox.getSelectionModel());

		return () -> {
			removeSelectionModelListener.run();
			comboBox.setConverter(null);
		};
	}

	private Runnable initSelectionModelListener(SelectionModel<K> selectionModel) {
		final ChangeListener<K> selectionModelListener = (observable, oldValue, newValue) -> {
			if (newValue != null) {

				Optional<T> selectedItemOptional = getModelByKey(newValue);

				selectedItemOptional.ifPresent(selectedItem::setValue);
			}
		};
		selectionModel.selectedItemProperty().addListener(selectionModelListener);

		ChangeListener<T> selectedItemListener = (observable, oldValue, newValue) -> {
			if (newValue != null) {
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


		Runnable removeSelectionModelListener = initSelectionModelListener(listView.getSelectionModel());

		return () -> {
			removeSelectionModelListener.run();
			listView.setCellFactory(null);
		};
	}

	@Override
	public Runnable connect(ChoiceBox<K> choiceBox) {
		choiceBox.setItems(listTransformation.getTargetList());

		choiceBox.setConverter(keyItemConverter);

		Runnable removeSelectionModelListener = initSelectionModelListener(choiceBox.getSelectionModel());

		return () -> {
			removeSelectionModelListener.run();
			choiceBox.setConverter(null);
		};
	}
}
