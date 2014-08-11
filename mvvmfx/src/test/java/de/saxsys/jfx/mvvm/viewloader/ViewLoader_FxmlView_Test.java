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

import java.io.IOException;
import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import de.saxsys.jfx.mvvm.testingutils.TestUtils;
import de.saxsys.jfx.mvvm.viewloader.example.InvalidFxmlTestView;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewMultipleViewModels;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewA;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewB;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModelA;
import javafx.fxml.LoadException;
import javafx.scene.layout.VBox;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlView;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewFxRoot;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewWithActionMethod;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewWithMissingController;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewWithWrongController;
import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewWithoutViewModel;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModel;

/**
 * Test the loading of FxmlViews.
 * 
 * @author manuel.mauky
 */
@RunWith(JfxRunner.class)
public class ViewLoader_FxmlView_Test {
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setup() throws Exception{
		resourceBundle = new PropertyResourceBundle(new StringReader(""));
	}
	
	
	@Test
	public void testLoadFxmlViewTuple() throws IOException {
		
		final ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class).resourceBundle(resourceBundle).load();
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		
		final TestFxmlView codeBehind = viewTuple.getCodeBehind();
		assertThat(codeBehind.getViewModel()).isNotNull();
		assertThat(codeBehind.resourceBundle).isEqualTo(resourceBundle);
		
		assertThat(codeBehind.viewModelWasNull).isFalse();
	}
	
	@Test
	public void testViewWithoutViewModel() {
		
		final ViewTuple viewTuple = FluentViewLoader.fxmlView(TestFxmlViewWithoutViewModel.class).load();
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestFxmlViewWithoutViewModel.class);
		
		final TestFxmlViewWithoutViewModel codeBehind = (TestFxmlViewWithoutViewModel) viewTuple.getCodeBehind();
		
		assertThat(codeBehind.wasInitialized).isTrue();
		assertThat(codeBehind.viewModel).isNull();
	}
	
	@Test
	public void testViewWithFxRoot() {
		TestFxmlViewFxRoot root = new TestFxmlViewFxRoot();
		
		ViewTuple<TestFxmlViewFxRoot, TestViewModel> viewTuple = FluentViewLoader.fxmlView(
				TestFxmlViewFxRoot.class).codeBehind(root).root(root).load();
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isEqualTo(root);
		assertThat(viewTuple.getCodeBehind()).isNotNull().isEqualTo(root);
		
		assertThat(viewTuple.getCodeBehind().viewModel).isNotNull();
		assertThat(viewTuple.getCodeBehind().viewModelWasNull).isFalse();
	}
	
	
	/**
	 * It is possible to use an existing instance of the codeBehind/controller. 
	 */
	@Test
	public void testUseExistingCodeBehind(){
		TestFxmlViewWithMissingController codeBehind = new TestFxmlViewWithMissingController();

		ViewTuple<TestFxmlViewWithMissingController, TestViewModel> viewTuple = 
				FluentViewLoader.fxmlView(TestFxmlViewWithMissingController.class).codeBehind(codeBehind).load();

		assertThat(viewTuple).isNotNull();

		assertThat(viewTuple.getCodeBehind()).isEqualTo(codeBehind);
		assertThat(viewTuple.getCodeBehind().viewModel).isNotNull();
	}
	
	/**
	 * When in the fxml file an action method is used that doesn't exists in the specified controller, we like to get
	 * the javafx exception that is thrown from the pure fxmlLoader.
	 */
	@Test
	public void testControllerHasNoActionMethodThatIsDeclaredInFxml() {
		try {
			ViewTuple<TestFxmlViewWithActionMethod, TestViewModel> viewTuple = FluentViewLoader.fxmlView(
					TestFxmlViewWithActionMethod.class).load();
			fail("A LoadException from FXMLLoader is expected");
		} catch (Exception e) {
			assertThat(e).hasCauseInstanceOf(LoadException.class).hasMessageContaining("onAction");
		}
	}
	
	@Test
	public void testLoadFxmlFailedWithWrongController() throws IOException {
		try {
			final ViewTuple viewTuple = FluentViewLoader.fxmlView(TestFxmlViewWithWrongController.class).load();
			fail("A LoadException from FXMLLoader is expected");
		} catch (Exception e) {
			assertThat(e).hasCauseInstanceOf(LoadException.class).hasRootCauseInstanceOf(ClassNotFoundException.class);

			Throwable cause = TestUtils.getRootCause(e);
			assertThat(cause).hasMessageContaining("WrongControllerNameTROLOLO");
		}
	}
	
	@Test
	public void testLoadFxmlFailedWithMissingController() throws IOException {
		try {
			final ViewTuple viewTuple = FluentViewLoader.fxmlView(TestFxmlViewWithMissingController.class).load();
			fail("A LoadException from FXMLLoader is expected");
		} catch (Exception e) {
			assertThat(e).hasCauseInstanceOf(IOException.class).hasMessageContaining(
					"Could not load the controller for the View");
		}
	}


	/**
	 * When there is already a Controller defined in the fxml file (fx:controller) then
	 * it is not possible to use an existing controller instance with the viewLoader. 
	 */
	@Test
	public void testUseExistingCodeBehindFailWhenControllerIsDefinedInFXML() {

		try {
			TestFxmlView codeBehind = new TestFxmlView(); // the fxml file for this class has a fx:controller defined.
			ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class).codeBehind(codeBehind).load();

			fail("Expected a LoadException to be thrown");
		}catch(Exception e) {
			assertThat(e).hasCauseInstanceOf(LoadException.class).hasMessageContaining(
					"Controller value already specified");
		}
	}

	@Test(expected = RuntimeException.class)
	public void testLoadFailNoSuchFxmlFile() {
		ViewTuple<InvalidFxmlTestView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(InvalidFxmlTestView.class).load();
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

		ViewTuple<TestFxmlViewWithMissingController, TestViewModel> viewTuple = FluentViewLoader.fxmlView(
				TestFxmlViewWithMissingController.class).codeBehind(codeBehind).load();

		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getCodeBehind().viewModel).isEqualTo(existingViewModel);
	}


	@Test
	public void testViewModelIsAvailableInViewTupleForFXMLView(){

		ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class).load();

		TestViewModel viewModel = viewTuple.getViewModel();

		assertThat(viewModel).isNotNull();
		assertThat(viewModel).isEqualTo(viewTuple.getCodeBehind().getViewModel());
	}


	@Test
	public void testThrowExceptionWhenMoreThenOneViewModelIsDefinedInFxmlView(){
		try{
			FluentViewLoader.fxmlView(TestFxmlViewMultipleViewModels.class).load();
			fail("Expecting an Exception because in the view class there are 2 viewmodels defined.");
		}catch(Exception e){
			assertThat(TestUtils.getRootCause(e)).isInstanceOf(RuntimeException.class).hasMessageContaining(
					"<2> viewModel fields");
		}
	}


	/**
	 * When a mvvmFX view A is part of another mvvmFX view B (i.e. referenced in the fxml file of B) 
	 * we have to verify that both A and B are correctly initialized and that the viewModels are injected.
	 */
	@Test
	public void testSubViewIsCorrectlyInitialized(){

		ViewTuple<TestViewA, TestViewModelA> viewTuple = FluentViewLoader.fxmlView(TestViewA.class).load();

		TestViewA codeBehindA = viewTuple.getCodeBehind();
		assertThat(codeBehindA).isNotNull();
		assertThat(codeBehindA.initializeWasCalled).isTrue();
		assertThat(codeBehindA.testViewB).isNotNull();
		assertThat(codeBehindA.viewModel).isNotNull();
		
		TestViewB codeBehindB = codeBehindA.testViewBController;
		
		assertThat(codeBehindB).isNotNull();
		assertThat(codeBehindB.initializeWasCalled).isTrue();
		assertThat(codeBehindB.viewModel).isNotNull();

	}


	/**
	 * It is possible to (re-)use an existing ViewModel when loading a view. A possible use case is when 
	 * you like to have 2 views that share the same viewModel instance.
	 */
	@Test
	public void testUseExistingViewModel(){
		
		TestViewModel viewModel = new TestViewModel();

		ViewTuple<TestFxmlView, TestViewModel> viewTupleOne = FluentViewLoader.fxmlView(TestFxmlView.class).viewModel
				(viewModel)
				.load();
		
		assertThat(viewTupleOne).isNotNull();
		
		assertThat(viewTupleOne.getCodeBehind().getViewModel()).isEqualTo(viewModel);
		assertThat(viewTupleOne.getViewModel()).isEqualTo(viewModel);


		ViewTuple<TestFxmlView, TestViewModel> viewTupleTwo = FluentViewLoader.fxmlView(TestFxmlView.class).viewModel
				(viewModel)
				.load();
		
		assertThat(viewTupleTwo).isNotNull();
		
		assertThat(viewTupleTwo.getViewModel()).isEqualTo(viewModel);
		assertThat(viewTupleTwo.getCodeBehind().getViewModel()).isEqualTo(viewModel);
		
		assertThat(viewTupleTwo.getCodeBehind()).isNotEqualTo(viewTupleOne.getCodeBehind());
	}

}
