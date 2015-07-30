package de.saxsys.mvvmfx.resourcebundle.included;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test usage of resourcebundles for fx:include views. This is a test to reproduce the bug #235 (https://github.com/sialcasa/mvvmFX/issues/235)
 * 
 * @author manuel.mauky
 */
@RunWith(JfxRunner.class)
public class IncludedViewsTest {
	
	
	private ResourceBundle root;
	private ResourceBundle included;
	
	@Before
	public void setup(){
		MvvmFX.setGlobalResourceBundle(null);
		root = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".root");
		included = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".included");
	}

	@After
	public void tearDown() {
		MvvmFX.setGlobalResourceBundle(null);
	}


	@Test
	public void testWithRootBundle(){

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).resourceBundle(root)
				.load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;
		
		assertThat(includedView).isNotNull();
		
		
		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
	@Test
	public void testWithGlobalBundle() {
		MvvmFX.setGlobalResourceBundle(root);

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;

		assertThat(includedView).isNotNull();


		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
	@Test
	public void testWithGlobalAndRoot() {
		MvvmFX.setGlobalResourceBundle(root);

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class)
				.resourceBundle(root)
				.load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;

		assertThat(includedView).isNotNull();


		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
	@Test
	public void testWithoutRootBundle() {

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;

		assertThat(includedView).isNotNull();


		assertThat(includedView.label.getText()).isEqualTo("included");
	}
	
}
