package de.saxsys.mvvmfx.resourcebundle.global;

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
 * Test global resource bundles. See https://github.com/sialcasa/mvvmFX/issues/234.
 * 
 * @author manuel.mauky
 */
@RunWith(JfxRunner.class)
public class GlobalResourceBundleTest {
	
	
	private ResourceBundle global;
	private ResourceBundle other;
	
	@Before
	public void setup(){
		MvvmFX.setGlobalResourceBundle(null);
		global = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".global");
		other = ResourceBundle.getBundle(this.getClass().getPackage().getName() + ".other");
	}
	
	@After
	public void tearDown() {
		MvvmFX.setGlobalResourceBundle(null);
	}
	
	@Test
	public void test() {
		MvvmFX.setGlobalResourceBundle(global);

		final ViewTuple<TestView, TestViewModel> viewTuple = FluentViewLoader.fxmlView(TestView.class).resourceBundle(other).load();
		final TestView codeBehind = viewTuple.getCodeBehind();
	
		assertThat(codeBehind.resources).isNotNull();
		
		assertThat(codeBehind.global_label.getText()).isEqualTo("global");
		assertThat(codeBehind.other_label.getText()).isEqualTo("other");
		
		// both "global" and "other" are containing the key "label". 
		// in this case "other" has the higher priority and overwrites the value defined in "global"
		assertThat(codeBehind.label.getText()).isEqualTo("other");
	}
	
	
}
