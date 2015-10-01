package de.saxsys.mvvmfx.testingutils.jfxrunner;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 
 * This test is used to check the compatibility of {@link JfxRunner}
 * with TestFX. Especially it should be possible to have both TestFX tests and JfxRunner tests in one build process.
 * 
 * @author manuel.mauky
 */
public class TestFxTest extends GuiTest {
	@Override
	protected Parent getRootNode() {
		final VBox root = new VBox();
		root.setId("root");
		return root;
	}
	
	@Test
	public void test() {
		final Node root = find("#root");
		
		assertThat(root).isNotNull();
	}
}
