/*******************************************************************************
 * Copyright 2013 Alexander Casall
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.jfx.mvvm.viewmodel.util.itemlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.ItemList;
import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.ModelToStringMapper;

/**
 * Tests for {@link ItemList}.
 * 
 * @author sialcasa
 * 
 */
public class ItemListTest {

	private static final String PERSON3_NAME = "Person3";
	private static final String PREFIX = "SOME WORDS ";
	// List which comes from the model and should be displayed in a view.
	private ObservableList<Person> listWithModelObjects;
	// Defines the mapping between model elements and view representation
	private ModelToStringMapper<Person> stringMapper;
	// New element which encapsulates and maps the 2 lists
	private ItemList<Person> itemList;

	/**
	 * Prepares the test.
	 */
	@Before
	public void init() {

		// Create the items in the model
		listWithModelObjects = FXCollections.observableArrayList();
		listWithModelObjects.add(new Person("Person1"));
		listWithModelObjects.add(new Person("Person2"));
		listWithModelObjects.add(new Person(PERSON3_NAME));

		// Create the mapper
		stringMapper = new ModelToStringMapper<Person>() {
			@Override
			public String toString(Person object) {
				return PREFIX + object.name;
			}
		};

		itemList = new ItemList<>(listWithModelObjects, stringMapper);
	}

	/**
	 * Checks whether the mapping from the model to the string representation
	 * works.
	 */
	@Test
	public void mapFromModelToString() {
		Assert.assertEquals(PREFIX + PERSON3_NAME, itemList
				.stringListProperty().get(2));
	}

	/**
	 * Check whether the string list changes when the item list changes (add
	 * item).
	 */
	@Test
	public void addItemToItemList() {
		Assert.assertEquals(3, itemList.stringListProperty().size());
		Assert.assertEquals(3, listWithModelObjects.size());
		listWithModelObjects.add(new Person("addedPerson"));
		Assert.assertEquals(4, itemList.stringListProperty().size());
		Assert.assertEquals(4, listWithModelObjects.size());
	}

	/**
	 * Check whether the string list changes when the item list changes (remove
	 * item).
	 */
	@Test
	public void removeItemFromList() {
		Assert.assertEquals(3, itemList.stringListProperty().size());
		Assert.assertEquals(3, listWithModelObjects.size());
		listWithModelObjects.remove(0);
		Assert.assertEquals(2, itemList.stringListProperty().size());
		Assert.assertEquals(2, listWithModelObjects.size());
	}

}
