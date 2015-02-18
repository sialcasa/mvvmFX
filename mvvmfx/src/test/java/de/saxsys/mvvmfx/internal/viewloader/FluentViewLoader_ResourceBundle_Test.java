package de.saxsys.mvvmfx.internal.viewloader;

import static org.assertj.core.api.Assertions.*;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.JavaView;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.example.TestFxmlViewResourceBundle;
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
		
		assertThat(view.resources).isNotNull().isEqualTo(resourceBundle);
		assertThat(view.resourceBundle).isNotNull().isEqualTo(resourceBundle);
		
		assertThat(viewModel.resourceBundle).isNotNull().isEqualTo(resourceBundle);
	}
	
	@Test
	public void success_fxml_injectionWithExistingViewModel(){
		TestViewModelWithResourceBundle viewModel = new TestViewModelWithResourceBundle();

		final ViewTuple<TestFxmlViewResourceBundle, TestViewModelWithResourceBundle> viewTuple = FluentViewLoader
				.fxmlView(TestFxmlViewResourceBundle.class)
				.resourceBundle(resourceBundle)
				.viewModel(viewModel)
				.load();
		
		assertThat(viewTuple.getViewModel()).isEqualTo(viewModel);
		final TestFxmlViewResourceBundle view = viewTuple.getCodeBehind();

		
		assertThat(view.resourceBundle).isNotNull().isEqualTo(resourceBundle);
		assertThat(viewModel.resourceBundle).isNotNull().isEqualTo(resourceBundle);
	}
	
	@Test
	public void fail_fxml_noResourceBundleProvidedOnLoad() {
		try{
			FluentViewLoader
					.fxmlView(TestFxmlViewResourceBundle.class)
					.load();
			fail("Expected an IllegalStateException");
		} catch (Exception e){
			assertThat(e).hasRootCauseInstanceOf(IllegalStateException.class);
		}
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

		assertThat(view.resourceBundle).isNotNull().isEqualTo(resourceBundle);

		assertThat(viewModel.resourceBundle).isNotNull().isEqualTo(resourceBundle);
	}

	@Test
	public void success_java_injectionWithExistingViewModel(){
		TestViewModelWithResourceBundle viewModel = new TestViewModelWithResourceBundle();

		final ViewTuple<TestJavaView, TestViewModelWithResourceBundle> viewTuple = FluentViewLoader
				.javaView(TestJavaView.class)
				.resourceBundle(resourceBundle)
				.viewModel(viewModel)
				.load();

		assertThat(viewTuple.getViewModel()).isEqualTo(viewModel);
		final TestJavaView view = viewTuple.getCodeBehind();


		assertThat(view.resourceBundle).isNotNull().isEqualTo(resourceBundle);
		assertThat(viewModel.resourceBundle).isNotNull().isEqualTo(resourceBundle);
	}

	@Test
	public void fail_java_noResourceBundleProvidedOnLoad() {
		try{
			FluentViewLoader
					.javaView(TestJavaView.class)
					.load();
			fail("Expected an IllegalStateException");
		} catch (Exception e){
			assertThat(e).isInstanceOf(IllegalStateException.class);
		}
	}
	
}
