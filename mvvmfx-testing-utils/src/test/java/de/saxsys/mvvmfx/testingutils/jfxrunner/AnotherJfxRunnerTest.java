package de.saxsys.mvvmfx.testingutils.jfxrunner;

import javafx.scene.control.TextField;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This is a second test of {@link JfxRunner} to make sure that more then one such tests can be executed in an
 *  automated build. 
 *  
 */
@RunWith(JfxRunner.class)
public class AnotherJfxRunnerTest {
	
	@Test
	public void test() {
		TextField textField = new TextField();
		
		assertThat(textField).isNotNull();
	}
	
}
