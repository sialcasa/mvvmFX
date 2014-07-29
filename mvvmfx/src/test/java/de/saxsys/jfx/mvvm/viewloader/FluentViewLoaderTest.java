package de.saxsys.jfx.mvvm.viewloader;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlViewFxRoot;
import javafx.scene.layout.VBox;

import org.junit.Test;

import de.saxsys.jfx.mvvm.viewloader.example.TestFxmlView;
import de.saxsys.jfx.mvvm.viewloader.example.TestJavaView;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModel;


/**
 * This test verifies the API of the {@link de.saxsys.jfx.mvvm.viewloader.FluentViewLoader}. The functionality of
 * loading Views is not part of this test as it is already tested in other tests for the ViewLoader itself.
 */
public class FluentViewLoaderTest {
	
	
	/// FXML VIEW ///
	
	@Test
	public void testLoadFxmlView() {
		ViewTuple<TestFxmlView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlView.class).load();
		
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getViewModel()).isNotNull();
		assertThat(viewTuple.getView()).isInstanceOf(VBox.class);
	}
	
	@Test
	public void testLoadFxmlViewWithResourceBundle() throws IOException{
		final ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));

		ViewTuple<TestFxmlView, TestViewModel> viewTuple  = FluentViewLoader.fxmlView(TestFxmlView.class).resourceBundle(resourceBundle).load();
		assertThat(viewTuple).isNotNull();
	}
	
	
	@Test
	public void testLoadFxRoot(){
		TestFxmlViewFxRoot fxRoot = new TestFxmlViewFxRoot();
		
		ViewTuple<TestFxmlViewFxRoot, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlViewFxRoot.class).codeBehind(fxRoot).root(fxRoot).load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getCodeBehind()).isEqualTo(fxRoot);
		assertThat(viewTuple.getView()).isEqualTo(fxRoot);
	}
	
	@Test
	public void testLoadFxmlViewWithAllParams() throws IOException{
		TestFxmlViewFxRoot customControl = new TestFxmlViewFxRoot();
		final ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));

		ViewTuple<TestFxmlViewFxRoot, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestFxmlViewFxRoot.class)
				.codeBehind(customControl)
				.resourceBundle(resourceBundle)
				.root(customControl)
				.load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getCodeBehind()).isEqualTo(customControl);
		assertThat(viewTuple.getView()).isEqualTo(customControl);
		
	}
	
	
	/// JAVA VIEW ///
	
	@Test
	public void testLoadJavaView() {
		ViewTuple<TestJavaView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestJavaView.class).load();
		
		assertThat(viewTuple.getCodeBehind()).isNotNull();
		assertThat(viewTuple.getViewModel()).isNotNull();
		assertThat(viewTuple.getView()).isInstanceOf(VBox.class);
	}
	
	@Test
	public void testLoadJavaViewWithResourceBundle() throws IOException {
		final ResourceBundle resourceBundle = new PropertyResourceBundle(new StringReader(""));
		
		ViewTuple<TestJavaView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestJavaView.class)
				.resourceBundle(resourceBundle).load();
		assertThat(viewTuple).isNotNull();
	}
}
