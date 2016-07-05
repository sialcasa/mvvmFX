package de.saxsys.mvvmfx.examples.itemlist;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.itemlist.model.IceCreamFlavor;
import de.saxsys.mvvmfx.examples.itemlist.model.IceCreamRepository;
import de.saxsys.mvvmfx.utils.newitemlist.ItemList;
import de.saxsys.mvvmfx.utils.newitemlist.ViewItemList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ItemListExampleViewModel implements ViewModel {

	private IceCreamRepository repository;

	private StringProperty favoriteLabelText = new SimpleStringProperty("");


	private ItemList<IceCreamFlavor, String> itemList = new ItemList<>(IceCreamFlavor::getId);

	public ItemListExampleViewModel(IceCreamRepository repository) {
		this.repository = repository;

		itemList.setLabelFunction(IceCreamFlavor::getName);

		favoriteLabelText.bind(Bindings.createStringBinding(() -> {
			IceCreamFlavor iceCreamFlavor = itemList.selectedItemProperty().get();

			if(iceCreamFlavor == null) {
				return "Your choice:";
			} else {
				return "Your choice: " + iceCreamFlavor.getName();
			}
		}, itemList.selectedItemProperty()));

	}

	public void initialize() {
		itemList.getModelList().addAll(repository.get());
	}

	public ViewItemList<String> itemList() {
		return itemList;
	}

	public StringProperty favoriteLabelTextProperty() {
		return favoriteLabelText;
	}
}
