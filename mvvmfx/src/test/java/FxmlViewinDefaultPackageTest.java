import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test reproduces the bug no 154 (https://github.com/sialcasa/mvvmFX/issues/156).
 * 
 * A FxmlView located in the default package couldn't be loaded because a NullPointerException was thrown.
 */
@RunWith(JfxRunner.class)
public class FxmlViewinDefaultPackageTest {
	
	
	@Test
	public void test() {
		
		ViewTuple<FxmlViewInDefaultPackage, TestViewModel> viewTuple = FluentViewLoader
				.fxmlView(FxmlViewInDefaultPackage.class).load();
		
		assertThat(viewTuple).isNotNull();
		assertThat(viewTuple.getView()).isNotNull();
	}
	
}
