package de.saxsys.mvvmfx.examples.itemlist;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.itemlist.model.IceCreamFlavor;
import de.saxsys.mvvmfx.examples.itemlist.model.IceCreamRepository;
import de.saxsys.mvvmfx.utils.newitemlist.ItemList;
import de.saxsys.mvvmfx.utils.newitemlist.ViewItemList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;
import java.util.UUID;

public class ItemListExampleViewModel implements ViewModel {

	private IceCreamRepository repository;

	private StringProperty favoriteLabelText = new SimpleStringProperty("");

	private ItemList<IceCreamFlavor, String> itemList = new ItemList<>(IceCreamFlavor::getId);

	private StringProperty newValueInput = new SimpleStringProperty();

	private ReadOnlyBooleanWrapper newValueEnabled = new ReadOnlyBooleanWrapper();

	public ItemListExampleViewModel(IceCreamRepository repository) {
		this.repository = repository;

		itemList.enableNoSelection(UUID.randomUUID().toString(), "---");

		itemList.setLabelFunction(IceCreamFlavor::getName);

		favoriteLabelText.bind(Bindings.createStringBinding(() -> {
			IceCreamFlavor iceCreamFlavor = itemList.selectedItemProperty().get();

			if(iceCreamFlavor == null) {
				return "Your choice:";
			} else {
				return "Your choice: " + iceCreamFlavor.getName();
			}
		}, itemList.selectedItemProperty()));

		itemList.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null) {
				newValueInput.setValue(newValue.getName());
			} else {
				newValueInput.setValue("");
			}
		});


		newValueEnabled.bind(itemList.selectedItemProperty().isNotNull());
	}

	public void initialize() {
		reloadFromRepository();
	}

	private void reloadFromRepository() {
		itemList.replaceModelItems(repository.get());
	}

	public void saveNewValue() {
		IceCreamFlavor selectedFlavor = itemList.selectedItemProperty().get();

		if(selectedFlavor != null) {
			String newValue = newValueInput.get();

			String oldValue = selectedFlavor.getName();

			if(!Objects.equals(newValue, oldValue)) {
				selectedFlavor.setName(newValue);

				repository.persist(selectedFlavor);
				reloadFromRepository();
			}
		}
	}

	public void clearSelection() {
		itemList.clearSelection();
	}

	public ViewItemList<String> itemList() {
		return itemList;
	}

	public StringProperty favoriteLabelTextProperty() {
		return favoriteLabelText;
	}

	public StringProperty newValueInputProperty() {
		return newValueInput;
	}

	public ReadOnlyBooleanProperty newValueEnabledProperty() {
		return newValueEnabled.getReadOnlyProperty();
	}
}
