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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import de.saxsys.jfx.mvvm.testingutils.GCVerifier;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import org.junit.Before;
import org.junit.Test;

import javax.print.DocFlavor;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Test to verify the behaviour of the {@link ListenerManager}.
 * 
 * @author manuel.mauky
 * 
 */
@SuppressWarnings({ "unchecked" })
public class ListenerManagerTest {
	
	private ChangeListener<String> changeListener = mock(ChangeListener.class);
	
	private ChangeListener<String> anotherListener = mock(ChangeListener.class);
	
	private ListChangeListener<String> listChangeListener = mock(ListChangeListener.class);
	
	private InvalidationListener invalidationListener = mock(InvalidationListener.class);
	
	private StringProperty stringProperty = new SimpleStringProperty();
	private ListProperty<String> simpleListProperty = new SimpleListProperty<>();
	
	private ListenerManager manager;
	
	@Before
	public void setup() {
		manager = new ListenerManager();
		simpleListProperty.set(FXCollections.<String> observableArrayList());
	}
	
	/**
	 * The ListenerManager adds the given Listener when the
	 * {@link ListenerManager#register(ObservableValue, ChangeListener)} method is called. When
	 * {@link ListenerManager#clean()} is called the listener is removed.
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
	 * A Listener that was added by hand isn't effected by the {@link ListenerManager#clean()} method and will still be
	 * called after the cleanup.
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
	
	/**
	 * A ChangeListener is added to a ListProperty. Verify that the listener is called and can be cleaned.
	 */
	@Test
	public void testListPropertyWithChangeListener() {
		manager.register(simpleListProperty, listChangeListener);
		
		simpleListProperty.add("test1");
		verify(listChangeListener, times(1)).onChanged(any(ListChangeListener.Change.class));
		
		// remove will fire the changeListener a second time.
		simpleListProperty.remove("test1");
		verify(listChangeListener, times(2)).onChanged(any(ListChangeListener.Change.class));
		
		manager.clean();
		simpleListProperty.add("test2");
		verifyNoMoreInteractions(listChangeListener);
	}
	
	/**
	 * An InvalidationListener is added to a Property. Verify that the listener is called and can be cleaned.
	 */
	@Test
	public void testListPropertyWithInvalidationListener() {
		manager.register(stringProperty, invalidationListener);
		
		stringProperty.set("test1");
		verify(invalidationListener).invalidated(stringProperty);
		
		manager.clean();
		stringProperty.set("test2");
		verifyNoMoreInteractions(invalidationListener);
	}

	/**
	 * This test is used to verify that when the property and listener are available for garbage collection
	 * when they aren't used anymore and only the listenerManager holds references to them.
	 */
	@Test
	public void testGC(){
		StringProperty property = new SimpleStringProperty();
	
		// We need to create a listener that uses objects from the outer scope
		// This is needed because otherwise the java compiler would transfer the lambda expression into a static method
		// and this static method can't be garbage collected. And this is absolutely ok because
		// when the listener has no references to the outside it can't prevent other instances from being collected.
		Object testObject = new Object();
		
		// A lambda expression that has references to the outside is comparable to an anonymous inner class. 
		// When this listener isn't correctly garbage collected it will also prevent the object which reference it has from
		// being collected (in this case "testObject"). This could produce a memory leak.
		ChangeListener<String> listener = (observable, oldValue, newValue) -> System.out.println(">" + newValue + "" + testObject);

		GCVerifier propertyVerifier = GCVerifier.create(property);
		GCVerifier listenerVerifier = GCVerifier.create(listener);
		

		manager.register(property, listener);
		
		property = new SimpleStringProperty();
		listener = null;
		
		// now there are no references to the property and listener anymore.
		// so we need to verify that the property and listener are available for garbage collection
		propertyVerifier.verify();
		listenerVerifier.verify();
	}
}
