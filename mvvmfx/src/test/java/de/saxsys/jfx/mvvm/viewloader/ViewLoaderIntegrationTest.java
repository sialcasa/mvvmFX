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

import static org.assertj.core.api.Assertions.*;

import de.saxsys.jfx.mvvm.TestUtils;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewMultipleViewModels;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewWithMissingController;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaViewMultipleViewModels;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaViewWithImplicitInit;
import javafx.fxml.LoadException;
import javafx.scene.layout.VBox;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.viewloader.example.InvalidFxmlTestView;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlView;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewWithoutViewModel;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaView;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaViewWithoutViewModel;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModel;
import org.junit.rules.ExpectedException;

/**
 * This test verifies the behaviour of the {@link de.saxsys.jfx.mvvm.viewloader.ViewLoader} class.
 * <p/>
 * The actual loading of views is only tested on the surface as there are tests for the specific viewLoaders of
 * different Viewtypes (see {@link de.saxsys.jfx.mvvm.viewloader.JavaViewLoaderTest} and
 * {@link de.saxsys.jfx.mvvm.viewloader .FxmlViewLoaderTest}).
 * 
 * The purpose of this test case is to check the integration of the specific viewLoaders and some error handling.
 * 
 * @author manuel.mauky, alexander.casall
 */
public class ViewLoaderIntegrationTest {
	
	private ViewLoader viewLoader;
	
	@Before
	public void setup() {
		viewLoader = new ViewLoader();
	}
	
	/**
	 * The purpose of this test case is to verify that the loading of JavaViews is working correctly. This contains the
	 * resolving of the view type and casting.
	 * <p/>
	 * The actual loading of JavaViews with the {@link de.saxsys.jfx.mvvm.viewloader.JavaViewLoader} is tested in
	 * {@link de.saxsys.jfx.mvvm.viewloader.JavaViewLoaderTest}.
	 */
	@Test
	public void testLoadJavaView() {
		ViewTuple<TestJavaView, TestViewModel> viewTuple = viewLoader.loadViewTuple(TestJavaView.class, null);
		
		assertThat(viewTuple).isNotNull();
	}
	
	/**
	 * The purpose of this test case is to verify that the loading of FxmlViews is working correctly. This contains the
	 * resolving of the view type and casting.
	 * <p/>
	 * The actual loading of JavaViews with the {@link de.saxsys.jfx.mvvm.viewloader.FxmlViewLoader} is tested in
	 * {@link de.saxsys.jfx.mvvm.viewloader.FxmlViewLoaderTest}.
	 */
	@Test
	public void testLoadFxmlView() {
		ViewTuple<TestFxmlView, TestViewModel> viewTuple = viewLoader.loadViewTuple(TestFxmlView.class, null);
		
		assertThat(viewTuple).isNotNull();
	}
	
	@Test
	public void testLoadWithStringPath() {
		ViewTuple<? extends View, ? extends ViewModel> viewTuple = viewLoader
				.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/example/TestFxmlView.fxml");
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestFxmlView.class);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadFailNoSuchFxmlFile() {
		ViewTuple<InvalidFxmlTestView, TestViewModel> viewTuple = viewLoader.loadViewTuple(InvalidFxmlTestView.class);
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadFailNoValidContentInFxmlFile() {
		ViewTuple<? extends View, ? extends ViewModel> viewTuple = viewLoader
				.loadViewTuple("/de/saxsys/jfx/mvvm/viewloader/example/wrong.fxml");
	}
	
	@Test
	public void testLoadJavaViewWithoutViewModel() {
		ViewTuple viewTuple = viewLoader.loadViewTuple(TestJavaViewWithoutViewModel.class);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull();
		assertThat(viewTuple.getCodeBehind()).isNotNull();
	}
	
	@Test
	public void testLoadFxmlViewWithoutViewModel() {
		ViewTuple viewTuple = viewLoader.loadViewTuple(TestFxmlViewWithoutViewModel.class);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull();
		assertThat(viewTuple.getCodeBehind()).isNotNull();
	}

	/**
	 * It is possible to use an existing instance of the codeBehind/controller. 
	 */
	@Test
	public void testUseExistingCodeBehind(){

		TestFxmlViewWithMissingController codeBehind = new TestFxmlViewWithMissingController();

		viewLoader.setCodeBehind(codeBehind);

		ViewTuple<TestFxmlViewWithMissingController, TestViewModel> viewTuple = viewLoader
				.loadViewTuple(TestFxmlViewWithMissingController.class);

		assertThat(viewTuple).isNotNull();

		assertThat(viewTuple.getCodeBehind()).isEqualTo(codeBehind);
		assertThat(viewTuple.getCodeBehind().viewModel).isNotNull();
	}

	/**
	 * When there is already a Controller defined in the fxml file (fx:controller) then
	 * it is not possible to use an existing controller instance with the viewLoader. 
	 */
	@Test
	public void testUseExistingCodeBehindFailWhenControllerIsDefinedInFXML() {
		
		try {
			TestFxmlView codeBehind = new TestFxmlView(); // the fxml file for this class has a fx:controller defined.
			
			viewLoader.setCodeBehind(codeBehind);

			ViewTuple<TestFxmlView, TestViewModel> viewTuple = viewLoader
					.loadViewTuple(TestFxmlView.class);
			
			fail("Expected a LoadException to be thrown");
		}catch(Exception e) {
			assertThat(e).hasCauseInstanceOf(LoadException.class).hasMessageContaining(
					"Controller value already specified");
		}
	}


	/**
	 * The user can define a codeBehind instance that should be used by the viewLoader.
	 * When this codeBehind instance has already has a ViewModel it should not be overwritten when the view is loaded.
	 */
	@Test
	public void testAlreadyExistingViewModelShouldNotBeOverwritten(){

		TestFxmlViewWithMissingController codeBehind = new TestFxmlViewWithMissingController();
		
		TestViewModel existingViewModel = new TestViewModel();
		
		codeBehind.viewModel = existingViewModel;
		
		viewLoader.setCodeBehind(codeBehind);

		ViewTuple<TestFxmlViewWithMissingController, TestViewModel> viewTuple = viewLoader
				.loadViewTuple(TestFxmlViewWithMissingController.class);

		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getCodeBehind().viewModel).isEqualTo(existingViewModel);
	}

	
	@Test
	public void testViewModelIsAvailableInViewTupleForFXMLView(){

		ViewTuple<TestFxmlView, TestViewModel> viewTuple = viewLoader
				.loadViewTuple(TestFxmlView.class);

		TestViewModel viewModel = viewTuple.getViewModel();
		
		assertThat(viewModel).isNotNull();
		assertThat(viewModel).isEqualTo(viewTuple.getCodeBehind().getViewModel());
	}
	
	@Test
	public void testViewModelIsAvailableInViewTupleForJavaView(){

		ViewTuple<TestJavaView, TestViewModel> viewTuple = viewLoader
				.loadViewTuple(TestJavaView.class);

		TestViewModel viewModel = viewTuple.getViewModel();
		
		assertThat(viewModel).isNotNull();
		assertThat(viewModel).isEqualTo(viewTuple.getCodeBehind().viewModel);
	}
	
	@Test
	public void testThrowExceptionWhenMoreThenOneViewModelIsDefinedInFxmlView(){
		try{
			viewLoader.loadViewTuple(TestFxmlViewMultipleViewModels.class);
			fail("Expecting an Exception because in the view class there are 2 viewmodels defined.");
		}catch(Exception e){
			assertThat(TestUtils.getRootCause(e)).isInstanceOf(RuntimeException.class).hasMessageContaining(
					"<2> viewModel fields");
		}
	}
	
	@Test
	public void testThrowExceptionWhenMoreThenOneViewModelIsDefinedInJavaView(){
		try{
			viewLoader.loadViewTuple(TestJavaViewMultipleViewModels.class);
			fail("Expecting an Exception because in the view class there are 2 viewmodels defined.");
		}catch(Exception e){
			assertThat(e).isInstanceOf(RuntimeException.class).hasMessageContaining("<2> viewModel fields");
		}
	}
}

