/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
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
package de.saxsys.mvvmfx.internal.viewloader;

import de.saxsys.mvvmfx.ViewTuple;
import org.junit.Test;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.example.TestFxmlView;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The purpose of this test is to check that mocking of view loading is possible.
 * 
 * In theory loading a view is something that involves the environment of the application and therefore goes beyond the
 * scope of a Unit-Test. So in most cases the developer shouldn't need to test the view loading with a unit test.
 * 
 * The MVVM pattern supports this by saying that the loading of another view is done in the View component and
 * <strong>not</strong> in the ViewModel and the View is not a target of unit-tests in MVVM.
 *
 * Only the conditions under which a view has to be loaded should be defined (and unit-tested) in the ViewModel.
 * 
 * The consequence of these statements is that testing the loading of a view isn't a first-class problem with MVVM. It
 * should only be needed in some rare corner-cases.
 * 
 * The API of MvvmFX reflects this: The methods in {@link FluentViewLoader} to load a view are static for a better
 * usability. They are not optimized for unit-testing and mocking.
 * 
 * This test will show that mocking the loading process is still possible with a little bit of extra code.
 * 
 */
public class MockableViewLoaderTest {
	
	public static class MyApplication {
		
		public FluentViewLoader.FxmlViewStep<TestFxmlView, TestViewModel> builder =
				FluentViewLoader.fxmlView(TestFxmlView.class);
		
		
		public ViewTuple<TestFxmlView, TestViewModel> viewTuple;
		
		public void methodToTest() {
			viewTuple = builder.load();
		}
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void test() {
		MyApplication application = new MyApplication();
		
		FluentViewLoader.FxmlViewStep mock = mock(FluentViewLoader.FxmlViewStep.class);
		
		ViewTuple<TestFxmlView, TestViewModel> viewTupleMock = mock(ViewTuple.class);
		when(mock.load()).thenReturn(viewTupleMock);
		
		
		application.builder = mock;
		application.methodToTest();
		
		assertThat(application.viewTuple).isNotNull().isSameAs(viewTupleMock);
	}
	
	
}
