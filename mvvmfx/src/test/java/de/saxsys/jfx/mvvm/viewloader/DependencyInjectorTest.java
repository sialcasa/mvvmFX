/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
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
package de.saxsys.jfx.mvvm.viewloader;

import static org.junit.Assert.fail;

import javafx.util.Callback;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * This test verifies the behaviour of the {@link de.saxsys.jfx.mvvm.viewloader.DependencyInjector}.
 */
public class DependencyInjectorTest {
	
	private DependencyInjector injector;
	
	/**
	 * This class can be instantiated without problems as it has no private or parameterized constructor.
	 */
	static class Example {
		String name = "default";
	}
	
	/**
	 * This class has a parameterized constructor and therefore can't be instantiated automatically.
	 */
	static class ExampleWithParamsConstructor {
		String name;
		
		public ExampleWithParamsConstructor(String name) {
			this.name = name;
		}
	}
	
	/**
	 * This class has a private constructor and therefore can't be instantiated automatically.
	 */
	static class ExampleWithPrivateConstructor {
		private ExampleWithPrivateConstructor() {
		}
	}
	
	@Before
	public void setup() {
		injector = new DependencyInjector();
	}
	
	/**
	 * When no customInjector callback is defined, the default instantiation mechanism is used to get an instance of the
	 * desired class.
	 * <p/>
	 * By default for every method call a new instance is created.
	 */
	@Test
	public void testGetInstanceOfWithoutCustomInjector() {
		Assert.assertFalse(injector.isCustomInjectorDefined());
		
		Example instance = injector.getInstanceOf(Example.class);
		
		Assert.assertNotNull(instance);
		
		
		Example otherInstance = injector.getInstanceOf(Example.class);
		Assert.assertNotNull(otherInstance);
		
		Assert.assertNotSame(instance, otherInstance);
	}
	
	
	/**
	 * When there is a customInjector callback defined, this callback has to be called to get an instance of the desired
	 * class.
	 */
	@Test
	public void testGetInstanceOfWithCustomInjector() {
		Callback<Class<?>, Object> customInjectorCallback = Mockito.mock(Callback.class);
		injector.setCustomInjector(customInjectorCallback);
		
		Assert.assertEquals(customInjectorCallback, injector.getCustomInjector());
		
		Example expectedInstance = new Example();
		expectedInstance.name = "expected";
		
		Mockito.when(customInjectorCallback.call(Example.class)).thenReturn(expectedInstance);
		
		
		Example instance = injector.getInstanceOf(Example.class);
		
		Mockito.verify(customInjectorCallback).call(Example.class);
		
		Assert.assertEquals(expectedInstance, instance);
		Assert.assertEquals(instance.name, "expected");
	}
	
	
	/**
	 * By default when no customInjector callback is defined the instances are created on the fly. But this is only
	 * possible for classes with public constructor without parameters.
	 * <p/>
	 * In this test an Exception is thrown because the desired class has no public constructor.
	 */
	@Test
	public void testGetInstanceOfExceptionBecauseOfPrivateConstructor() {
		try {
			ExampleWithPrivateConstructor instance = injector.getInstanceOf(ExampleWithPrivateConstructor.class);
			fail("There should be an IllegalAccessException");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getCause() instanceof IllegalAccessException);
		}
	}
	
	
	/**
	 * By default when no customInjector callback is defined the instances are created on the fly. But this is only
	 * possible for classes with public constructor without parameters.
	 * <p/>
	 * <p/>
	 * In this test an Exception is thrown because the desired class has only one public constructor that expects
	 * parameters.
	 */
	@Test
	public void testGetInstanceOfExceptionBecauseOfParameterizedConstructor() {
		try {
			ExampleWithParamsConstructor instance = injector.getInstanceOf(ExampleWithParamsConstructor.class);
			fail("There should be an InstantiationException");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getCause() instanceof InstantiationException);
		}
	}
	
}
