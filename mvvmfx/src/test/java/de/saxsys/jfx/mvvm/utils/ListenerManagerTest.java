/*******************************************************************************
 * Copyright 2013 manuel.mauky
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

import static org.mockito.Mockito.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test to verify the behaviour of the {@link ListenerManager}.
 * 
 * @author manuel.mauky
 * 
 */
public class ListenerManagerTest {

	private ChangeListener<String> changeListener = mock(ChangeListener.class);

	private ChangeListener<String> anotherListener = mock(ChangeListener.class);

	private StringProperty stringProperty = new SimpleStringProperty();

	private ListenerManager manager;

	@Before
	public void setup() {
		manager = new ListenerManager();
	}

	/**
	 * The ListenerManager adds the given Listener when the
	 * {@link ListenerManager#register(ObservableValue, ChangeListener)} method
	 * is called. When {@link ListenerManager#clean()} is called the listener is
	 * removed.
	 */
	@Test
	public void testStringPropertyListener() {
		manager.register(stringProperty, changeListener);

		stringProperty.set("test1");

		// the listener was added by the ListenerManager and so it is called
		// here.
		verify(changeListener).changed(stringProperty, null, "test1");

		manager.clean();

		// after cleanup the listener isn't called anymore.
		stringProperty.set("test2");

		verifyNoMoreInteractions(changeListener);
	}

	/**
	 * A Listener that was added by hand isn't effected by the
	 * {@link ListenerManager#clean()} method and will still be called after the
	 * cleanup.
	 */
	@Test
	public void testOtherListenersStillCalledAfterClean() {
		manager.register(stringProperty, changeListener);

		// add another listener by hand
		stringProperty.addListener(anotherListener);

		stringProperty.set("test1");

		// both listeners are called
		verify(anotherListener).changed(stringProperty, null, "test1");
		verify(changeListener).changed(stringProperty, null, "test1");

		manager.clean();

		stringProperty.set("test2");

		// the "managed" listener isn't called anymore ...
		verifyNoMoreInteractions(changeListener);

		// ... but the other listener that was added by hand is still active
		verify(anotherListener).changed(stringProperty, "test1", "test2");

	}

}
