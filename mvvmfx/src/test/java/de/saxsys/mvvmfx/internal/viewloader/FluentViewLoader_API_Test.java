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

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.example.TestFxmlViewFxRoot;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.scene.layout.VBox;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.internal.viewloader.example.TestFxmlView;
import de.saxsys.mvvmfx.internal.viewloader.example.TestJavaView;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;


/**
 * This test verifies the API of the {@link de.saxsys.mvvmfx.FluentViewLoader}. The functionality of loading Views is
 * not part of this test as it is already tested in other tests for the ViewLoader itself.
 */
public class FluentViewLoader_API_Test {
	
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setup() throws IOException {
		resourceBundle = new PropertyResourceBundle(new StringReader(""));
	}
	
	
	// / FXML VIEW ///
	
	@Test
	public void testLoadFxmlView() {
		ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class).load();
		
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getViewModel()).isNotNull();
		assertThat(viewTuple.getView()).isInstanceOf(VBox.class);
	}
	
	@Test
	public void testLoadFxmlViewWithResourceBundle() throws IOException {
		ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class)
				.resourceBundle(resourceBundle).load();
		assertThat(viewTuple).isNotNull();
	}
	
	
	@Test
	public void testLoadFxRoot() {
		TestFxmlViewFxRoot fxRoot = new TestFxmlViewFxRoot();
		
		ViewTuple<TestFxmlViewFxRoot, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlViewFxRoot.class)
				.codeBehind(fxRoot).root(fxRoot).load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getCodeBehind()).isEqualTo(fxRoot);
		assertThat(viewTuple.getView()).isEqualTo(fxRoot);
	}
	
	@Test
	public void testLoadFxmlViewWithAllParams() throws IOException {
		TestFxmlViewFxRoot customControl = new TestFxmlViewFxRoot();
		ViewTuple<TestFxmlViewFxRoot, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlViewFxRoot.class)
				.codeBehind(customControl)
				.resourceBundle(resourceBundle)
				.root(customControl)
				.load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getCodeBehind()).isEqualTo(customControl);
		assertThat(viewTuple.getView()).isEqualTo(customControl);
		
	}
	
	
	// / JAVA VIEW ///
	
	@Test
	public void testLoadJavaView() {
		ViewTuple<TestJavaView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestJavaView.class).load();
		
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getViewModel()).isNotNull();
		assertThat(viewTuple.getView()).isInstanceOf(VBox.class);
	}
	
	@Test
	public void testLoadJavaViewWithResourceBundle() throws IOException {
		ViewTuple<TestJavaView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestJavaView.class)
				.resourceBundle(resourceBundle).load();
		assertThat(viewTuple).isNotNull();
	}
}
