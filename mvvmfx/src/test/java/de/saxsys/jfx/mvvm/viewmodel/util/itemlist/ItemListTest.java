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
import de.saxsys.jfx.mvvm.base.viewmodel.util.itemlist.ModelToStringFunction;

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
	private ModelToStringFunction<Person> stringMapper;
	// New element which encapsulates and maps the 2 lists
	private ItemList<Person> itemList;
	private Person person1 = new Person("Person1");
	private Person person2 = new Person("Person2");
	private Person person3 = new Person(PERSON3_NAME);
	
	/**
	 * Prepares the test.
	 */
	@Before
	public void init() {
		
		// Create the items in the model
		listWithModelObjects = FXCollections.observableArrayList();
		listWithModelObjects.add(person1);
		listWithModelObjects.add(person2);
		listWithModelObjects.add(person3);
		
		// Create the mapper
		stringMapper = new ModelToStringFunction<Person>() {
			@Override
			public String apply(Person object) {
				return PREFIX + object.name;
			}
		};
		
		itemList = new ItemList<>(listWithModelObjects, stringMapper);
	}
	
	/**
	 * Checks whether the mapping from the model to the string representation works.
	 */
	@Test
	public void mapFromModelToString() {
		Assert.assertEquals(PREFIX + PERSON3_NAME, itemList
				.stringListProperty().get(2));
	}
	
	/**
	 * Check whether the string list changes when the item list changes (add item).
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
	 * Check whether the string list changes when the item list changes (remove item).
	 */
	@Test
	public void removeItemFromItemList() {
		Assert.assertEquals(3, itemList.stringListProperty().size());
		Assert.assertEquals(3, listWithModelObjects.size());
		listWithModelObjects.remove(0);
		Assert.assertEquals(2, itemList.stringListProperty().size());
		Assert.assertEquals(2, listWithModelObjects.size());
	}
	
	@Test
	public void removeMultipleItemsFromItemList() {
		listWithModelObjects.removeAll(person1, person2);
		Assert.assertEquals(1, listWithModelObjects.size());
		Assert.assertEquals(1, itemList.stringListProperty().size());
		Assert.assertEquals(person3, listWithModelObjects.get(0));
		Assert.assertEquals(PREFIX + PERSON3_NAME, itemList
				.stringListProperty().get(0));
	}
	
	@Test
	public void removeAllItemsFromItemList() {
		listWithModelObjects.clear();
		Assert.assertEquals(0, listWithModelObjects.size());
		Assert.assertEquals(0, itemList.stringListProperty().size());
	}
	
	@Test
	public void addItemToItemListAtIndex() {
		listWithModelObjects.add(1, new Person("addedPerson"));
		Assert.assertEquals(4, itemList.stringListProperty().size());
		Assert.assertEquals(4, listWithModelObjects.size());
		Assert.assertEquals(PREFIX + "addedPerson", itemList
				.stringListProperty().get(1));
	}
	
	@Test
	public void addMultipleItemsToItemList() {
		listWithModelObjects.addAll(new Person("added1"), new Person("added2"));
		Assert.assertEquals(5, listWithModelObjects.size());
		Assert.assertEquals(5, itemList.stringListProperty().size());
		Assert.assertEquals(PREFIX + "added1", itemList.stringListProperty()
				.get(3));
		Assert.assertEquals(PREFIX + "added2", itemList.stringListProperty()
				.get(4));
	}
	
	@Test
	public void replaceItemInItemListAtIndex() {
		listWithModelObjects.set(1, new Person("replacedPerson"));
		Assert.assertEquals(3, listWithModelObjects.size());
		Assert.assertEquals(3, itemList.stringListProperty().size());
		Assert.assertEquals(PREFIX + "replacedPerson", itemList
				.stringListProperty().get(1));
	}
	
}
