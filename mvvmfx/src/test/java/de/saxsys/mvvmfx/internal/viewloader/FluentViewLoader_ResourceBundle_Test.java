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

import static de.saxsys.mvvmfx.internal.viewloader.ResourceBundleAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.internal.viewloader.example.*;
import de.saxsys.mvvmfx.testingutils.ExceptionUtils;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.scene.layout.VBox;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.JavaView;
import de.saxsys.mvvmfx.ViewTuple;
import org.junit.runner.RunWith;

/**
 * This test focuses on the handling of {@link ResourceBundle}s.
 * <p>
 * A resourceBundle can be injected into the View with default behaviour of JavaFX. Additionally the user can use the
 * mvvmfx annotation {@link InjectResourceBundle} to inject the resourceBundle in the View and in the ViewModel.
 */
@RunWith(JfxRunner.class)
public class FluentViewLoader_ResourceBundle_Test {


	private ResourceBundle resourceBundle;
	private ResourceBundle globalResourceBundle;

	@Before
	public void setup() throws Exception {
		resourceBundle = new ListResourceBundle() {
			@Override
			protected Object[][] getContents() {
				return new Object[][] {
						{ "key_local1", "value_local1" },
						{ "key_local2", "value_local2" },
						{ "key_global1", "value_global1" } // overwrite global values
				};
			}
		};

		globalResourceBundle = new ListResourceBundle() {
			@Override
			protected Object[][] getContents() {
				return new Object[][] {
						{ "key_global1", "value_global1" },
						{ "key_global2", "value_global2" }
				};
			}
		};

		MvvmFX.setGlobalResourceBundle(null);
	}

	@After
	public void tearDown() {
		MvvmFX.setGlobalResourceBundle(null);
	}

	@Test
	public void success_fxml_injectionOfResourceBundles() {
		final ViewTuple<TestFxmlViewResourceBundle, TestViewModelWithResourceBundle> viewTuple =
				FluentViewLoader
						.fxmlView(TestFxmlViewResourceBundle.class)
						.resourceBundle(resourceBundle)
						.load();

		final TestViewModelWithResourceBundle viewModel = viewTuple.getViewModel();
		final TestFxmlViewResourceBundle view = viewTuple.getCodeBehind();

		assertThat(view.resources).isNotNull().hasSameContent(resourceBundle);
		assertThat(view.resourceBundle).isNotNull().hasSameContent(resourceBundle);

		assertThat(viewModel.resourceBundle).isNotNull().hasSameContent(resourceBundle);
	}

	/**
	 * This test case is similar to {@link #success_fxml_injectionOfResourceBundles()}
	 * with the only difference that this time the ViewModel isn't injected into the View.
	 * <p>
	 * This test reproduces the bug <a href="https://github.com/sialcasa/mvvmFX/issues/469">#469</a>
	 */
	@Test
	@Ignore("until fixed")
	public void success_fxml_injectionOfResourceBundlesVMisNotInjectedInView() {
		final ViewTuple<TestFxmlViewResourceBundleWithoutViewModelInjectedView, TestViewModelWithResourceBundle> viewTuple =
				FluentViewLoader
						.fxmlView(TestFxmlViewResourceBundleWithoutViewModelInjectedView.class)
						.resourceBundle(resourceBundle)
						.load();

		final TestViewModelWithResourceBundle viewModel = viewTuple.getViewModel();
		final TestFxmlViewResourceBundleWithoutViewModelInjectedView view = viewTuple.getCodeBehind();

		assertThat(view.resources).isNotNull().hasSameContent(resourceBundle);
		assertThat(view.resourceBundle).isNotNull().hasSameContent(resourceBundle);

		assertThat(viewModel.resourceBundle).isNotNull().hasSameContent(resourceBundle);
	}

	@Test
	public void success_fxml_injectionWithExistingViewModel() {
		TestViewModelWithResourceBundle viewModel = new TestViewModelWithResourceBundle();

		final ViewTuple<TestFxmlViewResourceBundle, TestViewModelWithResourceBundle> viewTuple = FluentViewLoader
				.fxmlView(TestFxmlViewResourceBundle.class)
				.resourceBundle(resourceBundle)
				.viewModel(viewModel)
				.load();

		assertThat(viewTuple.getViewModel()).isEqualTo(viewModel);
		final TestFxmlViewResourceBundle view = viewTuple.getCodeBehind();


		assertThat(view.resourceBundle).isNotNull().hasSameContent(resourceBundle);
		assertThat(view.resources).isNotNull().hasSameContent(resourceBundle);
		assertThat(viewModel.resourceBundle).isNotNull().hasSameContent(resourceBundle);
	}


	@Test
	public void success_fxml_existingCodeBehind() {
		TestFxmlViewResourceBundleWithoutController codeBehind = new TestFxmlViewResourceBundleWithoutController();

		final ViewTuple<TestFxmlViewResourceBundleWithoutController, TestViewModelWithResourceBundle> viewTuple =
				FluentViewLoader
						.fxmlView(TestFxmlViewResourceBundleWithoutController.class)
						.codeBehind(codeBehind)
						.resourceBundle(resourceBundle)
						.load();

		assertThat(viewTuple.getCodeBehind()).isEqualTo(codeBehind);
		final TestViewModelWithResourceBundle viewModel = viewTuple.getViewModel();

		assertThat(viewModel.resourceBundle).hasSameContent(resourceBundle);
		assertThat(codeBehind.resourceBundle).hasSameContent(resourceBundle);
	}

	@Test
	public void success_fxml_existingCodeBehind_and_existingViewModel() {
		TestFxmlViewResourceBundleWithoutController codeBehind = new TestFxmlViewResourceBundleWithoutController();
		TestViewModelWithResourceBundle viewModel = new TestViewModelWithResourceBundle();

		final ViewTuple<TestFxmlViewResourceBundleWithoutController, TestViewModelWithResourceBundle> viewTuple =
				FluentViewLoader
						.fxmlView(TestFxmlViewResourceBundleWithoutController.class)
						.codeBehind(codeBehind)
						.viewModel(viewModel)
						.resourceBundle(resourceBundle)
						.load();



		assertThat(viewTuple.getCodeBehind()).isEqualTo(codeBehind);
		assertThat(viewTuple.getViewModel()).isEqualTo(viewModel);

		assertThat(viewModel.resourceBundle).hasSameContent(resourceBundle);
		assertThat(codeBehind.resourceBundle).hasSameContent(resourceBundle);
	}

	public static class TestJavaView extends VBox implements JavaView<TestViewModelWithResourceBundle> {
		@InjectResourceBundle
		ResourceBundle resourceBundle;
	}


	@Test
	public void success_java_injectionOfResourceBundles() {
		final ViewTuple<TestJavaView, TestViewModelWithResourceBundle> viewTuple =
				FluentViewLoader
						.javaView(TestJavaView.class)
						.resourceBundle(resourceBundle)
						.load();

		final TestViewModelWithResourceBundle viewModel = viewTuple.getViewModel();
		final TestJavaView view = viewTuple.getCodeBehind();

		assertThat(view.resourceBundle).isNotNull().hasSameContent(resourceBundle);

		assertThat(viewModel.resourceBundle).isNotNull().hasSameContent(resourceBundle);
	}

	@Test
	public void success_java_injectionWithExistingViewModel() {
		TestViewModelWithResourceBundle viewModel = new TestViewModelWithResourceBundle();

		final ViewTuple<TestJavaView, TestViewModelWithResourceBundle> viewTuple = FluentViewLoader
				.javaView(TestJavaView.class)
				.resourceBundle(resourceBundle)
				.viewModel(viewModel)
				.load();

		assertThat(viewTuple.getViewModel()).isEqualTo(viewModel);
		final TestJavaView view = viewTuple.getCodeBehind();


		assertThat(view.resourceBundle).isNotNull().hasSameContent(resourceBundle);
		assertThat(viewModel.resourceBundle).isNotNull().hasSameContent(resourceBundle);
	}


	/**
	 * Both the View and ViewModel have {@link InjectResourceBundle} annotations (without optional argument)
	 * but no resourceBundle was provided at loading time. Therefore an exception is thrown.
	 */
	@Test
	//	@Ignore("until fixed. See issue #435")
	public void fail_noResourceBundleGivenForViewAndViewModel() {
		MvvmFX.setGlobalResourceBundle(null);

		try {
			FluentViewLoader.fxmlView(TestFxmlViewResourceBundle.class).load();

			failBecauseExceptionWasNotThrown(RuntimeException.class);
		} catch (Exception ex) {
			assertThat(ExceptionUtils.getRootCause(ex)).isInstanceOf(IllegalStateException.class).hasMessageContaining(
					"expects a ResourceBundle to be injected but no ResourceBundle was defined while loading.");
		}
	}

	/**
	 * Only the ViewModel is using the {@link InjectResourceBundle} annotation
	 * but no resourceBundle was provided at loading time. Therefore an exception is thrown.
	 */
	@Test
	public void fail_noResourceBundleGivenForViewModel() {
		MvvmFX.setGlobalResourceBundle(null);

		try {
			FluentViewLoader.fxmlView(TestFxmlViewOnlyViewModelResourceBundle.class).load();

			failBecauseExceptionWasNotThrown(RuntimeException.class);
		} catch (Exception ex) {
			assertThat(ExceptionUtils.getRootCause(ex)).isInstanceOf(IllegalStateException.class).hasMessageContaining(
					"expects a ResourceBundle to be injected but no ResourceBundle was defined while loading.");
		}
	}

	/**
	 * Only the View is using the {@link InjectResourceBundle} annotation
	 * but no resourceBundle was provided at loading time. Therefore an exception is thrown.
	 */
	@Test
	public void fail_noResourceBundleGivenForView() {
		MvvmFX.setGlobalResourceBundle(null);

		try {
			FluentViewLoader.fxmlView(TestFxmlViewOnlyViewResourceBundle.class).load();

			failBecauseExceptionWasNotThrown(RuntimeException.class);
		} catch (Exception ex) {
			assertThat(ExceptionUtils.getRootCause(ex)).isInstanceOf(IllegalStateException.class).hasMessageContaining(
					"expects a ResourceBundle to be injected but no ResourceBundle was defined while loading.");
		}
	}

}
