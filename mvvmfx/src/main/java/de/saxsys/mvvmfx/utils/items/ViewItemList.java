package de.saxsys.mvvmfx.utils.items;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public interface ViewItemList <K> {


	Runnable connect(ComboBox<K> comboBox);

	Runnable connect(ListView<K> listView);

	Runnable connect(ChoiceBox<K> choiceBox);



}
