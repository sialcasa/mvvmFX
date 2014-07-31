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
public class FxmlViewLoaderTest {
	
	private FxmlViewLoader fxmlViewLoader;
	
	@Before
	public void setup() {
		fxmlViewLoader = new FxmlViewLoader();
	}
	
	@Test
	public void testLoadFxmlViewTuple() throws IOException {
		final ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));
		
		final ViewTuple<TestFxmlView, TestViewModel> viewTuple = fxmlViewLoader.loadFxmlViewTuple(TestFxmlView.class,
				resourceBundle, null, null);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		
		final TestFxmlView codeBehind = viewTuple.getCodeBehind();
		assertThat(codeBehind.getViewModel()).isNotNull();
		assertThat(codeBehind.resourceBundle).isEqualTo(resourceBundle);
		
		assertThat(codeBehind.viewModelWasNull).isFalse();
	}
	
	@Test
	public void testLoadFxmlViewTupleWithoutViewModel() {
		
		final ViewTuple viewTuple = fxmlViewLoader.loadFxmlViewTuple(TestFxmlViewWithoutViewModel.class, null, null,
				null);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull().isInstanceOf(TestFxmlViewWithoutViewModel.class);
		
		final TestFxmlViewWithoutViewModel codeBehind = (TestFxmlViewWithoutViewModel) viewTuple.getCodeBehind();
		
		assertThat(codeBehind.wasInitialized).isTrue();
		assertThat(codeBehind.viewModel).isNull();
	}
	
	@Test
	public void testLoadFxmlViewWithFxRoot() {
		TestFxmlViewFxRoot root = new TestFxmlViewFxRoot();
		
		ViewTuple<TestFxmlViewFxRoot, TestViewModel> viewTuple = fxmlViewLoader.loadFxmlViewTuple(
				TestFxmlViewFxRoot.class, null, root, root);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isEqualTo(root);
		assertThat(viewTuple.getCodeBehind()).isNotNull().isEqualTo(root);
		
		assertThat(viewTuple.getCodeBehind().viewModel).isNotNull();
		assertThat(viewTuple.getCodeBehind().viewModelWasNull).isFalse();
	}
	
	/**
	 * When in the fxml file an action method is used that doesn't exists in the specified controller, we like to get
	 * the javafx exception that is thrown from the pure fxmlLoader.
	 */
	@Test
	public void testControllerHasNoActionMethodThatIsDeclaredInFxml() {
		try {
			ViewTuple<TestFxmlViewWithActionMethod, TestViewModel> viewTuple = fxmlViewLoader.loadFxmlViewTuple(
					TestFxmlViewWithActionMethod.class, null, null, null);
			fail("A LoadException from FXMLLoader is expected");
		} catch (Exception e) {
			assertThat(e).hasCauseInstanceOf(LoadException.class).hasMessageContaining("onAction");
		}
	}
	
	@Test
	public void testLoadFxmlFailedWithWrongController() throws IOException {
		try {
			final ViewTuple viewTuple = fxmlViewLoader.loadFxmlViewTuple(TestFxmlViewWithWrongController.class, null,
					null, null);
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
			final ViewTuple viewTuple = fxmlViewLoader.loadFxmlViewTuple(TestFxmlViewWithMissingController.class, null,
					null, null);
			fail("A LoadException from FXMLLoader is expected");
		} catch (Exception e) {
			assertThat(e).hasCauseInstanceOf(IOException.class).hasMessageContaining(
					"Could not load the controller for the View");
		}
	}
}
