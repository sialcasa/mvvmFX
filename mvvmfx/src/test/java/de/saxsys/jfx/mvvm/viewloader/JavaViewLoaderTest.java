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
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.jfx.mvvm.api.JavaView;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaView;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaViewWithImplicitInit;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaViewWithoutViewModel;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModel;

/**
 * Test the loading of JavaViews.
 * 
 * @author manuel.mauky
 */
public class JavaViewLoaderTest {
	
	private JavaViewLoader javaViewLoader;
	
	@Before
	public void setup() {
		javaViewLoader = new JavaViewLoader();
	}
	
	@Test
	public void testLoadJavaViewTuple() throws IOException {
		ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));
		
		ViewTuple<TestJavaView, TestViewModel> viewTuple = javaViewLoader.loadJavaViewTuple(TestJavaView.class,
				resourceBundle);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		
		TestJavaView codeBehind = viewTuple.getCodeBehind();
		assertThat(codeBehind.viewModel).isNotNull();
		assertThat(codeBehind.resourceBundle).isEqualTo(resourceBundle);
		
		assertThat(codeBehind.viewModelWasNull).isFalse();
	}
	
	
	@Test
	public void testLoadJavaViewTupleWithoutViewModel() {
		
		ViewTuple viewTuple = javaViewLoader.loadJavaViewTuple(TestJavaViewWithoutViewModel.class, null);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		
		final TestJavaViewWithoutViewModel codeBehind = (TestJavaViewWithoutViewModel) viewTuple.getCodeBehind();
		
		assertThat(codeBehind.wasInitialized).isTrue();
		assertThat(codeBehind.viewModel).isNull();
	}
	
	/**
	 * The purpose of this test case is to verify that the implicit initialization for java views is working the in a
	 * similar way as it does for fxml views by the {@link javafx.fxml.FXMLLoader}.
	 * 
	 * The naming convention is:
	 * <ul>
	 * 
	 * <li>a public field of type {@link java.util.ResourceBundle} named "resources" gets the current ResourceBundle
	 * injected.</li>
	 * <li>a public no-arg method named "initialize" is called after the injection of other resources is finished.</li>
	 * 
	 * </ul>
	 * 
	 * The third convention of the FXMLLoader to inject the path of the FXML file to a public field of type
	 * {@link java.net.URL} named "location" is NOT done by mvvmfx because it doesn't make sense for Java written Views
	 * (as there is no FXML file at all).
	 * 
	 * @throws IOException
	 */
	@Test
	public void testImplicitInitialization() throws IOException {
		ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));
		
		ViewTuple<TestJavaViewWithImplicitInit, TestViewModel> viewTuple = javaViewLoader.loadJavaViewTuple(
				TestJavaViewWithImplicitInit.class, resourceBundle);
		
		assertThat(viewTuple).isNotNull();
		
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		
		TestJavaViewWithImplicitInit codeBehind = viewTuple.getCodeBehind();
		assertThat(codeBehind.viewModel).isNotNull();
		assertThat(codeBehind.resources).isEqualTo(resourceBundle);
		
		assertThat(codeBehind.wasInitialized).isTrue();
		assertThat(codeBehind.viewModelWasNull).isFalse();
		assertThat(codeBehind.resourcesWasNull).isFalse();
	}
	
	
	
	/**
	 * Like the conventions of the FXMLLoader the implicit initialization and injection of ResourceBundles should not be
	 * made when the {@link javafx.fxml.Initializable} interface is NOT implemented.
	 */
	@Test
	public void implicitInitializationShouldNotBeExecutedWhenInitializableInterfaceIsImplemented() {
		
		ResourceBundle noResourceBundle = null;
		
		final ViewTuple<TestViewInitialize, TestViewModel> viewTuple = javaViewLoader.loadJavaViewTuple(TestViewInitialize.class,
				noResourceBundle);
		
		TestViewInitialize view = viewTuple.getCodeBehind();
		
		assertThat(view.explicitInitWasCalled).isTrue();
		assertThat(view.implicitInitWasCalled).isFalse();
	}
	
	/**
	 * This test class is used by
	 * {@link #implicitInitializationShouldNotBeExecutedWhenInitializableInterfaceIsImplemented()}.
	 */
	public static class TestViewInitialize extends VBox implements JavaView<TestViewModel>, Initializable {
		
		public boolean explicitInitWasCalled = false;
		public boolean implicitInitWasCalled = false;
		
		@Override
		public void initialize(URL url, ResourceBundle resourceBundle) {
			explicitInitWasCalled = true;
		}
		
		public void initialize() {
			implicitInitWasCalled = true;
		}
	}
	
	/**
	 * The implicit initialize method should only be called when it is defined to be public.
	 */
	@Test
	public void implicitInitializeShouldOnlyBeExecutedWhenItsPublic() {
		
		class TestViewProtectedInitialize extends VBox implements JavaView<TestViewModel> {
			public boolean implicitInitWasCalled = false;
			
			protected void initialize() {
				implicitInitWasCalled = true;
			}
		}
		
		TestViewProtectedInitialize view = new TestViewProtectedInitialize();
		javaViewLoader.callInitialize(view);
		
		assertThat(view.implicitInitWasCalled).isFalse();
	}
	
	/**
	 * The ResourceBundle should only be injected implicitly when the field is declared public.
	 */
	@Test
	public void implicitInjectionOfResourceBundleShouldOnlyBeDoneWhenItIsPublic() throws IOException {
		
		class TestViewProtectedInitialize extends VBox implements JavaView<TestViewModel> {
			protected ResourceBundle resources;
		}
		
		TestViewProtectedInitialize view = new TestViewProtectedInitialize();
		
		ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));
		javaViewLoader.injectResourceBundle(view, resourceBundle);
		
		assertThat(view.resources).isNull();
	}
	
	
	/**
	 * According to the conventions of the {@link javafx.fxml.FXMLLoader} the method "initialize" is only invoked
	 * implicitly when it has no parameters.
	 */
	@Test
	public void implicitInitializeMethodMayNotHaveParameters() {
		class TestViewInitializeWithParams extends VBox implements JavaView<TestViewModel> {
			public boolean implicitInitWasCalled = false;
			
			public void initialize(URL location, ResourceBundle resources) {
				implicitInitWasCalled = true;
			}
		}
		
		TestViewInitializeWithParams view = new TestViewInitializeWithParams();
		javaViewLoader.callInitialize(view);
		
		assertThat(view.implicitInitWasCalled).isFalse();
	}


	/**
	 * When an exception happens in the implicit initialize method it has to reach the user.
	 */
	@Test
	public void testExceptionInImplicitInitializeMethod(){
		
		
		try{
			ViewTuple<TestViewImplicitInitializeWithException, TestViewModel> viewTuple = javaViewLoader
					.loadJavaViewTuple(TestViewImplicitInitializeWithException.class, null);
			fail("Expected an Exception");
		}catch(Exception e){
			assertThat(e).isInstanceOf(IllegalStateException.class).hasMessage("TEST");
		}
	}

	/**
	 * This class is used in {@link #testExceptionInImplicitInitializeMethod()}
	 */
	public static class TestViewImplicitInitializeWithException extends VBox implements JavaView<TestViewModel> {
		public void initialize(){
			throw new IllegalStateException("TEST");
		}
	} 
	
	/**
	 * For explicit initialize methods (those defined by the {@link javafx.fxml.Initializable} interface) 
	 * apply the same rules as for implicit ones: The user needs to be able to handle the exception.
	 */
	@Test
	public void testExceptionInExplicitInitializeMethod(){
		try{
			ViewTuple<TestViewExplicitInitializeWithException, TestViewModel> viewTuple = javaViewLoader
					.loadJavaViewTuple(TestViewExplicitInitializeWithException.class, null);
			fail("Expected an Exception");
		}catch(Exception e){
			assertThat(e).isInstanceOf(IllegalStateException.class).hasMessage("TEST");
		}
	}

	/**
	 * This class is used in {@link #testExceptionInExplicitInitializeMethod()}.
	 */
	public static class TestViewExplicitInitializeWithException extends VBox implements JavaView<TestViewModel> , Initializable{
		@Override public void initialize(URL location, ResourceBundle resources) {
			throw new IllegalStateException("TEST");
		}
	}
}
