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

package de.saxsys.mvvmfx.utils.itemlist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SelectableItemList}.
 * 
 * @author sialcasa
 * 
 */
public class SelectableItemListTest {
	
	private final Person person3 = new Person("Person 1");
	private final Person person2 = new Person("Person 2");
	private final Person person1 = new Person("Person 3");
	private ModelToStringFunction<Person> stringMapper;
	private ObservableList<Person> listWithModelObjects;
	private SelectableItemList<Person> selectableItemList;
	
	/**
	 * Prepares the test.
	 */
	@BeforeEach
	public void init() {
		
		// Create the items in the model
		listWithModelObjects = FXCollections.observableArrayList();
		listWithModelObjects.add(person1);
		listWithModelObjects.add(person2);
		listWithModelObjects.add(person3);
		
		// Create the converter
		stringMapper = new ModelToStringFunction<Person>() {
			@Override
			public String apply(Person object) {
				return object.name;
			}
		};
		
		// Convenience
		selectableItemList = new SelectableItemList<Person>(
				listWithModelObjects, stringMapper);
	}
	
	/**
	 * Check whether the internal state is correct.
	 */
	@Test
	public void checkStartState() {
		Assertions.assertEquals(-1, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(null, selectableItemList.getSelectedItem());
	}
	
	/**
	 * Check whether the {@link SelectableItemList#selectedItem} changes when the
	 * {@link SelectableItemList#selectedIndexProperty()} was changed.
	 */
	@Test
	public void setSelectedItemByIndex() {
		selectableItemList.select(1);
		Assertions.assertEquals(listWithModelObjects.get(1), selectableItemList
				.selectedItemProperty().get());
	}
	
	/**
	 * Check whether the {@link SelectableItemList#selectedIndexProperty()} changes when the
	 * {@link SelectableItemList#selectedItem} was changed.
	 */
	@Test
	public void setSelectedIndexByItem() {
		selectableItemList.select(person3);
		Assertions.assertEquals(2, selectableItemList.getSelectedIndex());
	}
	
	/**
	 * Check whether changes are refused when an invalid {@link SelectableItemList#selectedItem} was set.
	 */
	@Test
	public void setSelectedIndexWithInvalidItem() {
		selectableItemList.select(person1);
		Assertions.assertEquals(0, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person1, selectableItemList.getSelectedItem());
		selectableItemList.select(new Person("Roflcopter"));
		Assertions.assertEquals(0, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person1, selectableItemList.getSelectedItem());
		selectableItemList.select(person2);
		Assertions.assertEquals(1, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person2, selectableItemList.getSelectedItem());
	}
	
	/**
	 * Check whether changes are refused when an invalid {@link SelectableItemList#selectedItem} was set.
	 */
	@Test
	public void setSelectedItemWithInvalidIndex() {
		selectableItemList.select(person1);
		Assertions.assertEquals(0, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person1, selectableItemList.getSelectedItem());
		selectableItemList.select(100);
		Assertions.assertEquals(0, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person1, selectableItemList.getSelectedItem());
	}
	
	@Test
	public void unselectBySettingSelectedItemToNull() {
		selectableItemList.select(person2);
		Assertions.assertEquals(1, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person2, selectableItemList.selectedItemProperty()
				.get());
		selectableItemList.select(null);
		Assertions.assertEquals(-1, selectableItemList.getSelectedIndex());
		Assertions.assertNull(selectableItemList.selectedItemProperty().get());
	}
	
	@Test
	public void unselectBySettingSelectedIndexToMinus1() {
		selectableItemList.select(person2);
		Assertions.assertEquals(1, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person2, selectableItemList.selectedItemProperty()
				.get());
		selectableItemList.select(-1);
		Assertions.assertEquals(-1, selectableItemList.getSelectedIndex());
		Assertions.assertNull(selectableItemList.selectedItemProperty().get());
	}
	
	@Test
	public void unselectByClearSelection() {
		selectableItemList.select(person2);
		Assertions.assertEquals(1, selectableItemList.getSelectedIndex());
		Assertions.assertEquals(person2, selectableItemList.selectedItemProperty()
				.get());
		selectableItemList.clearSelection();
		Assertions.assertEquals(-1, selectableItemList.getSelectedIndex());
		Assertions.assertNull(selectableItemList.selectedItemProperty().get());
	}
	
}
