package de.saxsys.mvvmfx.resourcebundle;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import org.junit.Before;
import org.junit.Ignore;
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
	private ResourceBundle sub;
	
	@Before
	public void setup(){
		root = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".root");
		sub = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".sub");
	}
	
	@Test
	@Ignore("ignore until fixed")
	public void testWithRootBundle(){

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).resourceBundle(root)
				.load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;
		
		assertThat(includedView).isNotNull();
		
		
		assertThat(includedView.label.getText()).isEqualTo("sub");
	}
	
	@Test
	@Ignore("ignore until fixed")
	public void testWithoutRootBundle() {

		final ViewTuple<RootView, RootViewModel> viewTuple = FluentViewLoader.fxmlView(RootView.class).load();

		final RootView rootView = viewTuple.getCodeBehind();

		final IncludedView includedView = rootView.includedViewController;

		assertThat(includedView).isNotNull();


		assertThat(includedView.label.getText()).isEqualTo("sub");
	}
	
}
