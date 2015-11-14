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
import static org.assertj.core.api.Assertions.fail;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModelA;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModelB;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModelWithResourceBundle;
import de.saxsys.mvvmfx.testingutils.ExceptionUtils;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;


/**
 * This test verifies that loading views of type {@link de.saxsys.mvvmfx.JavaView} works correctly.
 * 
 * This includes the handling of initialization and injection of the ViewModel and the resourceBundle.
 * 
 * The injection and initialization is similar to that of FXML files. It can be done explicit by the use of the
 * {@link javafx.fxml.Initializable} interface or implicit by using the naming conventions of the
 * {@link javafx.fxml.FXMLLoader}.
 * 
 * This naming conventions are:
 * <ul>
 *
 * <li>a public field of type {@link java.util.ResourceBundle} named "resources" gets the current ResourceBundle
 * injected.</li>
 * <li>a public no-arg method named "initialize" is called after the injection of other resources is finished.</li>
 *
 * </ul>
 *
 * The third convention of the FXMLLoader to inject the path of the FXML file to a public field of type
 * {@link java.net.URL} named "location" is NOT done by mvvmfx because it doesn't make sense for Java written Views (as
 * there is no FXML file at all).
 */
public class FluentViewLoader_JavaView_Test {
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void before() throws Exception {
		resourceBundle = new PropertyResourceBundle(new StringReader(""));
		
		
		// This custom dependency injector is used to be able to instantiate inner classes. This way every test case can
		// define
		// its own class for the conditions to test without the need to create a new file.
		MvvmFX.setCustomDependencyInjector(type -> {
			try {
				Constructor<?> constructor = type.getDeclaredConstructors()[0];
				
				if (constructor.getParameters().length == 0) {
					return constructor.newInstance();
				} else {
					return constructor.newInstance(FluentViewLoader_JavaView_Test.this);
				}
			} catch (Exception e) {
				fail("Test View can't be instantiated. This is a problem with the test code");
				return null;
			}
		});
	}
	
	@After
	public void after() {
		MvvmFX.setCustomDependencyInjector(null);
	}
	
	
	/**
	 * Verify that the loaded {@link de.saxsys.mvvmfx.ViewTuple} contains all expected references.
	 */
	@Test
	public void testViewTuple() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			@InjectViewModel
			private TestViewModel viewModel;
		}
		
		ViewTuple<TestView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestView.class).load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getView()).isNotNull().isInstanceOf(VBox.class);
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getViewModel()).isNotNull();
	}
	
	@Test
	public void test_initializeOfViewModel() {
		// given
		TestViewModelWithResourceBundle.wasInitialized = false;
		TestViewModelWithResourceBundle.resourceBundleWasAvailableAtInitialize = false;
		
		class TestView extends VBox implements JavaView<TestViewModelWithResourceBundle> {
		}
		
		// when
		FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load();
		
		// then
		assertThat(TestViewModelWithResourceBundle.wasInitialized).isTrue();
		assertThat(TestViewModelWithResourceBundle.resourceBundleWasAvailableAtInitialize).isTrue();
	}
	
	
	/**
	 * A JavaView has to extend from a javafx component to be loaded.
	 */
	@Test
	public void testViewDoesntExtendFromANode() {
		class TestView implements JavaView<TestViewModel> {
		}
		try {
			ViewTuple<TestView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestView.class).load();
			fail("Expected an exception");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(
					"extend from javafx.scene.Parent");
		}
	}
	
	
	/**
	 * It is possible to define a view without specifying a viewModel type.
	 */
	@Test
	public void testViewWithoutViewModelType() {
		class TestView extends VBox implements JavaView {
			@InjectViewModel
			public TestViewModel viewModel;
		}
		
		ViewTuple viewTuple = FluentViewLoader.javaView(TestView.class).load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getViewModel()).isNull();
		
		View codeBehind = viewTuple.getCodeBehind();
		assertThat(codeBehind).isNotNull().isInstanceOf(TestView.class);
		
		TestView loadedView = (TestView) codeBehind;
		
		assertThat(loadedView.viewModel).isNull();
	}
	
	/**
	 * The ViewModel has to be injected before the explicit initialize method is called.
	 */
	@Test
	public void testViewModelWasInjectedBeforeExplicitInitialize() {
		class TestView extends VBox implements JavaView<TestViewModel>, Initializable {
			@InjectViewModel
			public TestViewModel viewModel;
			public boolean viewModelWasInjected = false;
			
			@Override
			public void initialize(URL location, ResourceBundle resources) {
				viewModelWasInjected = viewModel != null;
			}
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).load().getCodeBehind();
		
		assertThat(loadedView.viewModel).isNotNull();
		assertThat(loadedView.viewModelWasInjected).isTrue();
	}
	
	/**
	 * The ViewModel has to be injected before the explicit initialize method is called.
	 */
	@Test
	public void testViewModelWasInjectedBeforeImplicitInitialize() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			@InjectViewModel
			public TestViewModel viewModel;
			public boolean viewModelWasInjected = false;
			
			public void initialize() {
				viewModelWasInjected = viewModel != null;
			}
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).load().getCodeBehind();
		
		assertThat(loadedView.viewModel).isNotNull();
		assertThat(loadedView.viewModelWasInjected).isTrue();
	}
	
	
	
	@Test
	public void testThrowExceptionWhenMoreThenOneViewModelIsDefinedInJavaView() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			@InjectViewModel
			public TestViewModel viewModel1;
			
			@InjectViewModel
			public TestViewModel viewModel2;
		}
		
		try {
			FluentViewLoader.javaView(TestView.class).load();
			fail("Expecting an Exception because in the view class there are 2 viewmodels defined.");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(RuntimeException.class).hasMessageContaining("<2> viewModel fields");
		}
	}

	@Test
	public void testThrowExceptionWhenWrongViewModelTypeIsInjected() {
		class TestView extends VBox implements JavaView<TestViewModelA> {
			@InjectViewModel
			public TestViewModelB viewModel;
		}
		
		
		try {
			FluentViewLoader.javaView(TestView.class).load();
			fail("Expected an Exception");
		} catch (Exception e) {
			assertThat(ExceptionUtils.getRootCause(e)).isInstanceOf(RuntimeException.class).hasMessageContaining("field doesn't match the generic ViewModel type ");
		}
	}

	/**
	 * The {@link InjectViewModel} annotation may only be used on fields whose Type are implementing {@link ViewModel}.
	 */
	@Test
	public void testThrowExceptionWhenInjectViewModelAnnotationIsUsedOnOtherType() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			@InjectViewModel
			public Object viewModel;
		}
		
		
		try {
			FluentViewLoader.javaView(TestView.class).load();
			fail("Expected an Exception");
		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			assertThat(rootCause).isInstanceOf(RuntimeException.class).hasMessageContaining("doesn't implement the 'ViewModel' interface");
		}
	}
	
	/**
	 * When the ViewModel isn't injected in the view it should still be available in the ViewTuple.
	 */
	@Test
	public void testViewModelIsAvailableInViewTupleEvenIfItIsntInjectedInTheView() {
		
		class TestView extends VBox implements JavaView<TestViewModel> {
		}
		
		ViewTuple<TestView, TestViewModel> viewTuple = FluentViewLoader
				.javaView(TestView.class).load();
		
		assertThat(viewTuple.getViewModel()).isNotNull();
	}
	
	
	@Test
	public void testUseExistingViewModel() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			@InjectViewModel
			public TestViewModel viewModel;
		}
		
		TestViewModel viewModel = new TestViewModel();
		
		ViewTuple<TestView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestView.class).viewModel(viewModel)
				.load();
		
		assertThat(viewTuple.getCodeBehind().viewModel).isEqualTo(viewModel);
		assertThat(viewTuple.getViewModel()).isEqualTo(viewModel);
	}
	
	
	/**
	 * When the initialize method throws a checked exception, this exception is captured and wrapped in a runtime
	 * exception while loading.
	 */
	@Test
	public void testInitializeMethodThrowsCheckedException() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			public void initialize() throws Exception {
				throw new Exception("My test exception");
			}
		}
		
		try {
			FluentViewLoader.javaView(TestView.class).load();
			fail("Expected a RuntimeException");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(RuntimeException.class).hasCauseExactlyInstanceOf(Exception.class);
			
			Throwable cause = e.getCause();
			
			assertThat(cause).hasMessage("My test exception");
		}
	}
	
	/**
	 * When the initialize method throws an unchecked exception, this exception is directly rethrown while loading.
	 */
	@Test
	public void testInitializeMethodThrowsUncheckedException() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			public void initialize() throws Exception {
				throw new RuntimeException("My test exception");
			}
		}
		
		try {
			FluentViewLoader.javaView(TestView.class).load();
			fail("Expected a RuntimeException");
		} catch (Exception e) {
			assertThat(e).isInstanceOf(RuntimeException.class).hasMessage("My test exception");
			
			assertThat(e.getCause()).isNull();
		}
	}
	
	/**
	 * When the initialize method is private the method is not executed but the view should be loaded without errors.
	 */
	@Test
	public void testInitializeMethodIsPackageScoped() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			void initialize() throws Exception {
				fail("The initialize method may not be called because it is not public");
			}
		}
		
		assertThat(FluentViewLoader.javaView(TestView.class).load()).isNotNull();
	}
	
	
	/**
	 * When the {@link javafx.fxml.Initializable} interface is implemented, the implicit initialize method may not be
	 * called.
	 */
	@Test
	public void testImplicitInitializeIsNotCalledWhenInitializableIsImplemented() {
		class TestView extends VBox implements JavaView<TestViewModel>, Initializable {
			public boolean implicitInitializeWasCalled = false;
			public boolean explicitInitializeWasCalled = false;
			
			public void initialize() {
				implicitInitializeWasCalled = true;
			}
			
			@Override
			public void initialize(URL location, ResourceBundle resources) {
				explicitInitializeWasCalled = true;
			}
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).load().getCodeBehind();
		
		assertThat(loadedView.implicitInitializeWasCalled).isFalse();
		assertThat(loadedView.explicitInitializeWasCalled).isTrue();
	}
	
	
	/**
	 * According to the conventions of the {@link javafx.fxml.FXMLLoader} the method "initialize" is only invoked
	 * implicitly when it has no parameters.
	 */
	@Test
	public void implicitInitializeMethodMayNotHaveParameters() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			public void initialize(URL location, ResourceBundle resources) {
				fail("Initialize method may not be called when it has parameters");
			}
		}
		
		FluentViewLoader.javaView(TestView.class).load().getCodeBehind();
	}
	
	@Test
	public void testResourcesFieldIsInjectedImplicit() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			public ResourceBundle resources;
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load()
				.getCodeBehind();
		
		assertThat(loadedView.resources).hasSameContent(resourceBundle);
	}
	
	
	/**
	 * The resourceBundle has to be injected before the implicit initialize method is called.
	 */
	@Test
	public void testResourceBundleIsInjectedBeforeImplicitInitialize() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			public ResourceBundle resources;
			
			public boolean resourcesWasInjected = false;
			
			public void initialize() {
				resourcesWasInjected = resources != null;
			}
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load()
				.getCodeBehind();
		
		assertThat(loadedView.resourcesWasInjected).isTrue();
	}
	
	
	
	@Test
	public void testResourcesFieldIsNotInjectedWhenItIsNotPublic() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			ResourceBundle resources; // this time the field is package scoped
			
			public ResourceBundle getResources() {
				return resources;
			}
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load()
				.getCodeBehind();
		
		assertThat(loadedView.getResources()).isNull();
	}
	
	/**
	 * The naming conventions say that the field for the resourceBundle may be named "resources". The injection is still
	 * working when the type of the field is not {@link java.util.ResourceBundle}.
	 */
	@Test
	public void testResourcesFieldHasOtherTypeAndIsStillInjected() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			public Object resources;
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load()
				.getCodeBehind();
		
		assertThat(loadedView.resources).isInstanceOf(ResourceBundle.class);
		ResourceBundle resourceBundle = (ResourceBundle)loadedView.resources;
		assertThat(resourceBundle).isNotNull().hasSameContent(resourceBundle);
	}
	
	/**
	 * When the naming conventions for the ResourceBundle are meet but the type of the field is not assignable the
	 * ResourceBundle of cause can't be injected. But even in this case the view has to be loaded normally and no
	 * exception may be thrown.
	 */
	@Test
	public void testResourcesFieldHasNonAssignableType() {
		class TestView extends VBox implements JavaView<TestViewModel> {
			public String resources;
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load()
				.getCodeBehind();
		
		assertThat(loadedView.resources).isNull();
	}
	
	@Test
	public void testResourceBundleIsPassedToExplicitInitialize() {
		class TestView extends VBox implements JavaView<TestViewModel>, Initializable {
			private ResourceBundle resourceBundle;
			
			@Override
			public void initialize(URL location, ResourceBundle resources) {
				resourceBundle = resources;
			}
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load()
				.getCodeBehind();
		
		assertThat(loadedView.resourceBundle).hasSameContent(resourceBundle);
	}
	
	/**
	 * When the {@link javafx.fxml.Initializable} interface is implemented, no implicit injection should be done.
	 */
	@Test
	public void testResourceBundleIsNotInjectedImplicitWhenInitializeableIsImplemented() {
		class TestView extends VBox implements JavaView<TestViewModel>, Initializable {
			public ResourceBundle resources; // this fulfills the naming convention
			
			@Override
			public void initialize(URL location, ResourceBundle resources) {
			}
		}
		
		TestView loadedView = FluentViewLoader.javaView(TestView.class).resourceBundle(resourceBundle).load()
				.getCodeBehind();
		
		assertThat(loadedView.resources).isNull();
	}
	
}
