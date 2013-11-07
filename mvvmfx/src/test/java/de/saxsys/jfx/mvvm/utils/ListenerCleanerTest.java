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
package de.saxsys.jfx.mvvm.utils;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link ListenerCleaner}.
 * 
 * @author sialcasa
 */
public class ListenerCleanerTest {
	private ListChangeListener<String> listchangelistener = new ListChangeListener<String>() {

		@Override
		public void onChanged(
				javafx.collections.ListChangeListener.Change<? extends String> arg0) {
			calls++;
		}
	};

	private ChangeListener<String> changeListener = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> arg0,
				String arg1, String arg2) {
			calls++;
		}
	};

	private StringProperty stringProperty = new SimpleStringProperty();
	private ListProperty<String> simpleListProperty = new SimpleListProperty<String>();
	private ListenerCleaner cleaner;
	int calls = 0;

	@Before
	public void init() {
		calls = 0;
		cleaner = new ListenerCleaner();
		simpleListProperty.set(FXCollections.<String> observableArrayList());
	}

	/**
	 * When the cleaner is called it should be removed from the registrated
	 * property.
	 */
	@Test
	public void putAListenerAndClean() throws Exception {
		cleaner.put(stringProperty, changeListener);
		stringProperty.addListener(changeListener);
		stringProperty.set("test1");
		Assert.assertEquals(1, calls);
		stringProperty.set("test2");
		Assert.assertEquals(2, calls);
		cleaner.clean();
		stringProperty.set("test3");
		Assert.assertEquals(2, calls);
	}

	/**
	 * When the cleaner is called it should be removed from the registrated
	 * property.
	 */
	@Test
	public void putAListChangeListenerAndClean() throws Exception {
		cleaner.put(simpleListProperty, listchangelistener);
		simpleListProperty.addListener(listchangelistener);
		simpleListProperty.add("test1");
		Assert.assertEquals(1, calls);
		simpleListProperty.remove("test1");
		Assert.assertEquals(2, calls);
		cleaner.clean();
		simpleListProperty.add("test3");
		Assert.assertEquals(2, calls);
	}

	/**
	 * When the cleaner is called it should not remove the added and removed
	 * property listenerpair. property.
	 */
	@Test
	public void putAListenerAndRemove() throws Exception {
		cleaner.put(stringProperty, changeListener);
		stringProperty.addListener(changeListener);
		stringProperty.set("test1");
		Assert.assertEquals(1, calls);
		stringProperty.set("test2");
		Assert.assertEquals(2, calls);
		cleaner.remove(stringProperty, changeListener);
		cleaner.clean();
		stringProperty.set("test3");
		Assert.assertEquals(3, calls);
	}

	/**
	 * When the cleaner is called it should not remove the added and removed
	 * property listenerpair. property.
	 */
	@Test
	public void putAListChangeListenerAndRemove() throws Exception {
		cleaner.put(simpleListProperty, listchangelistener);
		simpleListProperty.addListener(listchangelistener);
		simpleListProperty.add("test1");
		Assert.assertEquals(1, calls);
		simpleListProperty.remove("test1");
		Assert.assertEquals(2, calls);
		cleaner.remove(simpleListProperty, listchangelistener);
		cleaner.clean();
		simpleListProperty.add("test3");
		Assert.assertEquals(3, calls);
	}
}
