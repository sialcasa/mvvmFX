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
package de.saxsys.jfx.mvvm.cdi.internal;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Assert;
import org.junit.Test;

import de.saxsys.jfx.mvvm.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;

/**
 * This test verifies that a simple application can be started with the CDI/Weld extension. When there is a problem with
 * the cdi configuration, f.e. ambiguous dependencies for an internal class of mvvmfx, this kind of bug should be found
 * by this test case.
 */
public class CdiInjectorTest {
	
	static class Example {
		@Inject
		NotificationCenter notificationCenter;
		
		@Inject
		ViewLoader viewLoader;
	}
	
	/**
	 * This test case starts the cdi container and tries to get an instance of the example class. This test will fail if
	 * there is a problem with the dependency injection configuration for the defined dependencies.
	 *
	 */
	@Test
	public void testCdiInjector() {
		WeldContainer weldContainer = new Weld().initialize();
		
		CdiInjector cdiInjector = weldContainer.instance().select(CdiInjector.class).get();
		
		Object exampleObject = cdiInjector.call(Example.class);
		Assert.assertNotNull(exampleObject);
		
		Assert.assertTrue(exampleObject instanceof Example);
		
		Example example = (Example) exampleObject;
		
		Assert.assertNotNull(example.notificationCenter);
		Assert.assertNotNull(example.viewLoader);
		
	}
}
