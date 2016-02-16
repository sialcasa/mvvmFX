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

import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javafx.scene.layout.VBox;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.JavaView;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.example.TestFxmlViewResourceBundle;
import de.saxsys.mvvmfx.internal.viewloader.example.TestFxmlViewResourceBundleWithoutController;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModelWithResourceBundle;


/**
 * This test focuses on the handling of {@link ResourceBundle}s.
 * 
 * A resourceBundle can be injected into the View with default behaviour of JavaFX. Additionally the user can use the
 * mvvmfx annotation {@link InjectResourceBundle} to inject the resourceBundle in the View and in the ViewModel.
 */
public class FluentViewLoader_ResourceBundle_Test {
	
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setup() throws Exception {
		resourceBundle = new PropertyResourceBundle(new StringReader(""));
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
}
