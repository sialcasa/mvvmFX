package de.saxsys.mvvm.base.viewmodel.util.itemlist;

/*
 * Copyright 2013 Alexander Casall - Saxonia Systems AG
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.ItemList;

/**
 * Tests for {@link ItemList}.
 * 
 * @author sialcasa
 * 
 */
public class ItemListTest {
	private StringConverter<Integer> stringConverter;
	private ObservableList<Integer> itemList;
	private ItemList<Integer> selectableItemList;

	/**
	 * Prepares the test.
	 */
	@Before
	public void init() {

		// Create the items
		itemList = FXCollections.observableArrayList();
		itemList.clear();
		itemList.add(1);
		itemList.add(2);
		itemList.add(3);

		// Create the converter
		stringConverter = new StringConverter<Integer>() {
			@Override
			public Integer fromString(String arg0) {
				return Integer.parseInt(arg0);
			}

			@Override
			public String toString(Integer arg0) {
				return arg0.toString();
			}
		};

		// Convenience
		selectableItemList = new ItemList<Integer>(itemList, stringConverter);
	}

	/**
	 * Check whether the string list changes when the item list changes.
	 */
	@Test
	public void addItemToItemList() {
		Assert.assertEquals(3, selectableItemList.stringListProperty().size());
		itemList.add(4);
		Assert.assertEquals(4, selectableItemList.stringListProperty().size());
	}
}
