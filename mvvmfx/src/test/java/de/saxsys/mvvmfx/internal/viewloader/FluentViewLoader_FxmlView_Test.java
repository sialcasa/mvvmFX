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
package de.saxsys.mvvmfx.internal.viewloader;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.example.*;
import de.saxsys.mvvmfx.testingutils.ExceptionUtils;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.fxml.LoadException;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static de.saxsys.mvvmfx.internal.viewloader.ResourceBundleAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Test the loading of FxmlViews.
 * 
 * @author manuel.mauky
 */
@RunWith(JfxRunner.class)
public class FluentViewLoader_FxmlView_Test {
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setup() throws Exception {
		resourceBundle = new PropertyResourceBundle(new StringReader(""));
	}
	
	
	@Test
	public void testLoadFxmlViewTuple() throws IOException {
		
		TestFxmlView.instanceCounter = 0;
		TestViewModel.instanceCounter = 0;
		
		final ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class)
				.resourceBundle(resourceBundle).load();

		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		
		final TestFxmlView codeBehind = viewTuple.getCodeBehind();
		assertThat(codeBehind.getViewModel()).isNotNull();
		assertThat(codeBehind.resourceBundle).hasSameContent(resourceBundle);
		
		assertThat(codeBehind.viewModelWasNull).isFalse();
		
		assertThat(TestFxmlView.instanceCounter).isEqualTo(1);
		assertThat(TestViewModel.instanceCounter).isEqualTo(1);
	}
	
	
	@Test
	public void test_initializeOfViewModel() {
		// given
		TestViewModelWithResourceBundle.wasInitialized = false;
		TestViewModelWithResourceBundle.resourceBundleWasAvailableAtInitialize = false;
		
		// when
		
		final ViewTuple<TestFxmlViewResourceBundle, TestViewModelWithResourceBundle> viewTuple = FluentViewLoader
				.fxmlView(TestFxmlViewResourceBundle.class)
				.resourceBundle(resourceBundle).load();

		// then
		assertThat(TestViewModelWithResourceBundle.wasInitialized).isTrue();
		assertThat(TestViewModelWithResourceBundle.resourceBundleWasAvailableAtInitialize).isTrue();
	}
	
	
	/**
	 * This is a test case to reproduce bug #181 (<a href="https://github.com/sialcasa/mvvmFX/issues/181">issues
	 * 181</a>).
	 * 
	 * When the View has no field for the ViewModel, then there are two instances created of the ViewModel.
	 */
	@Test
	public void testBugMultipleViewModelsCreated() {
		TestFxmlViewWithoutViewModelField.instanceCounter = 0;
		TestViewModel.instanceCounter = 0;
		
		final ViewTuple<TestFxmlViewWithoutViewModelField, TestViewModel> viewTuple = FluentViewLoader
				.fxmlView(TestFxmlViewWithoutViewModelField.class).load();

		assertThat(TestFxmlViewWithoutViewModelField.instanceCounter).isEqualTo(1);
		assertThat(TestViewModel.instanceCounter).isEqualTo(1);
	}
	
	@Test
	public void testViewWithoutViewModelType() {
		
		final ViewTuple viewTuple = FluentViewLoader.fxmlView(TestFxmlViewWithoutViewModelType.class).load();
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestFxmlViewWithoutViewModelType.class);
		
		final TestFxmlViewWithoutViewModelType codeBehind = (TestFxmlViewWithoutViewModelType) viewTuple
				.getCodeBehind();

		assertThat(codeBehind.wasInitialized).isTrue();
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
	public void testUseExistingCodeBehind() {
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
			
			Throwable cause = ExceptionUtils.getRootCause(e);
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
	 * When there is already a Controller defined in the fxml file (fx:controller) then it is not possible to use an
	 * existing controller instance with the viewLoader.
	 */
	@Test
	public void testUseExistingCodeBehindFailWhenControllerIsDefinedInFXML() {
		
		try {
			TestFxmlView codeBehind = new TestFxmlView(); // the fxml file for this class has a fx:controller defined.
			ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class)
					.codeBehind(codeBehind).load();

			fail("Expected a LoadException to be thrown");
		} catch (Exception e) {
			assertThat(e).hasCauseInstanceOf(LoadException.class).hasMessageContaining(
					"Controller value already specified");
		}
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoadFailNoSuchFxmlFile() {
		ViewTuple<InvalidFxmlTestView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(InvalidFxmlTestView.class)
				.load();
	}
	
	/**
	 * The user can define a codeBehind instance that should be used by the viewLoader. When this codeBehind instance
	 * has already has a ViewModel it should not be overwritten when the view is loaded.
	 */
	@Test
	public void testAlreadyExistingViewModelShouldNotBeOverwritten() {

		TestFxmlViewWithMissingController codeBehind = new TestFxmlViewWithMissingController();

		TestViewModel existingViewModel = new TestViewModel();

		codeBehind.viewModel = existingViewModel;

		ViewTuple<TestFxmlViewWithMissingController, TestViewModel> viewTuple = FluentViewLoader
				.fxmlView(TestFxmlViewWithMissingController.class).codeBehind(codeBehind).load();

		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getCodeBehind().viewModel).isEqualTo(existingViewModel);
	}
	
	
	@Test
	public void testViewModelIsAvailableInViewTupleForFXMLView() {
		
		ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class).load();
		
		TestViewModel viewModel = viewTuple.getViewModel();
		
		assertThat(viewModel).isNotNull();
		assertThat(viewModel).isEqualTo(viewTuple.getCodeBehind().getViewModel());
	}
	
	
	@Test
	public void testThrowExceptionWhenMoreThenOneViewModelIsDefinedInFxmlView() {
		try {
			FluentViewLoader.fxmlView(TestFxmlViewMultipleViewModels.class).load();
			fail("Expecting an Exception because in the view class there are 2 viewmodels defined.");
		} catch (Exception e) {
			assertThat(ExceptionUtils.getRootCause(e)).isInstanceOf(RuntimeException.class).hasMessageContaining(
					"<2> viewModel fields");
		}
	}
	
	
	@Test
	public void testViewHasNoGenericViewModelTypeButInjectsViewModel() {
		try {
			FluentViewLoader.fxmlView(TestFxmlViewWithoutViewModelTypeButWithInjection.class).load();
			fail("Expected an Exception");
		} catch (Exception e) {
			assertThat(ExceptionUtils.getRootCause(e)).isInstanceOf(RuntimeException.class)
					.hasMessageContaining("but tries to inject a viewModel");
		}
	}
	
	
	/**
	 * like {@link #testViewHasNoGenericViewModelTypeButInjectsViewModel()} but this time the field in the View is of
	 * type {@link ViewModel}.
	 */
	@Test
	public void testViewHasNoGenericViewModelTypeButInjectsViewModelOfIntefaceType() {
		try {
			FluentViewLoader.fxmlView(TestFxmlViewWithoutViewModelTypeButWithInjection2.class).load();
			fail("Expected an Exception");
		} catch (Exception e) {
			assertThat(ExceptionUtils.getRootCause(e)).isInstanceOf(RuntimeException.class)
					.hasMessageContaining("but tries to inject a viewModel");
		}
	}
	
	
	@Test
	public void testThrowExceptionWhenWrongViewModelTypeIsInjected() {
		try {
			FluentViewLoader.fxmlView(TestFxmlViewWithWrongInjectedViewModel.class).load();
			fail("Expected an Exception");
		} catch (Exception e) {
			assertThat(ExceptionUtils.getRootCause(e)).isInstanceOf(RuntimeException.class)
					.hasMessageContaining("field doesn't match the generic ViewModel type ");
		}
	}
	
	/**
	 * The {@link InjectViewModel} annotation may only be used on fields whose Type are implementing {@link ViewModel}.
	 */
	@Test
	public void testThrowExceptionWhenInjectViewModelAnnotationIsUsedOnOtherType() {
		try {
			FluentViewLoader.fxmlView(TestFxmlViewWithWrongAnnotationUsage.class).load();
			fail("Expected an Exception");
		} catch (Exception e) {
			assertThat(ExceptionUtils.getRootCause(e)).isInstanceOf(RuntimeException.class).hasMessageContaining("doesn't implement the 'ViewModel' interface");
		}
	}
	
	/**
	 * When a mvvmFX view A is part of another mvvmFX view B (i.e. referenced in the fxml file of B) we have to verify
	 * that both A and B are correctly initialized and that the viewModels are injected.
	 */
	@Test
	public void testSubViewIsCorrectlyInitialized() {
		
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
	 * It is possible to (re-)use an existing ViewModel when loading a view. A possible use case is when you like to
	 * have 2 views that share the same viewModel instance.
	 */
	@Test
	public void testUseExistingViewModel() {
		
		TestViewModel viewModel = new TestViewModel();
		
		ViewTuple<TestFxmlView, TestViewModel> viewTupleOne = FluentViewLoader.fxmlView(TestFxmlView.class)
				.viewModel(viewModel)
				.load();

		assertThat(viewTupleOne).isNotNull();
		
		assertThat(viewTupleOne.getCodeBehind().getViewModel()).isEqualTo(viewModel);
		assertThat(viewTupleOne.getViewModel()).isEqualTo(viewModel);
		
		
		ViewTuple<TestFxmlView, TestViewModel> viewTupleTwo = FluentViewLoader.fxmlView(TestFxmlView.class)
				.viewModel(viewModel)
				.load();

		assertThat(viewTupleTwo).isNotNull();
		
		assertThat(viewTupleTwo.getViewModel()).isEqualTo(viewModel);
		assertThat(viewTupleTwo.getCodeBehind().getViewModel()).isEqualTo(viewModel);
		
		assertThat(viewTupleTwo.getCodeBehind()).isNotEqualTo(viewTupleOne.getCodeBehind());
	}
	
	/**
	 * When the ViewModel isn't injected in the view it should still be available in the ViewTuple.
	 */
	@Test
	public void testViewModelIsAvailableInViewTupleEvenIfItIsntInjectedInTheView() {
		
		ViewTuple<TestFxmlViewWithoutViewModelField, TestViewModel> viewTuple = FluentViewLoader
				.fxmlView(TestFxmlViewWithoutViewModelField.class).load();
				
		assertThat(viewTuple.getCodeBehind().wasInitialized).isTrue();
		
		assertThat(viewTuple.getViewModel()).isNotNull();
		
	}

	/**
	 * This test reproduces the <a href="https://github.com/sialcasa/mvvmFX/issues/292">bug #292</a>
	 * Given the following conditions:
	 * 1. The View has no ViewModel field and not injection of the ViewModel.
	 * 2. While loading an existing ViewModel instance is passed to the {@link FluentViewLoader}
	 * 
	 * Under this conditions the ViewLoader was still creating a new ViewModel instance or retrieved an instance
	 * from DI. This isn't expected because the user has passed an existing ViewModel instance into the ViewLoader.
	 * 
	 */
	@Test
	public void testExistingViewModelWithoutInjectionInView() {
		DependencyInjector.getInstance().setCustomInjector(type -> {
			if(type.equals(TestViewModel.class)) {
				fail("An instance of TestViewModel was requested!");
				throw new IllegalStateException("An instance of TestViewModel was requested!");
			} else {
				try {
					return type.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		
		TestViewModel viewModel = new TestViewModel();

		final ViewTuple<TestFxmlViewWithoutViewModelField, TestViewModel> viewTuple = FluentViewLoader
				.fxmlView(TestFxmlViewWithoutViewModelField.class).viewModel(viewModel).load();

		assertThat(viewTuple.getViewModel()).isEqualTo(viewModel);
		
		// we need to reset the DI
		DependencyInjector.getInstance().setCustomInjector(null);
	}
}
