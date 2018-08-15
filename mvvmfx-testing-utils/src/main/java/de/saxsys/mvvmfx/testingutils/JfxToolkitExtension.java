package de.saxsys.mvvmfx.testingutils;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * This JUnit 5 extension can be used to start a JavaFX Thread before executing the tests.
 * This way a JavaFX Thread will be available in your tests so that you can use <code>Platform.runLater</code>
 * and similar constructs in your tests.
 *
 * However, this extension will not execute your Test method on the JavaFX Thread.
 * To execute some test code on the JavaFX Thread you should use {@link FxTestingUtils#runInFXThread(Runnable)}.
 */
public class JfxToolkitExtension implements BeforeAllCallback {
	@Override
	public void beforeAll(ExtensionContext extensionContext) throws Exception {
		new JFXPanel();
	}
}
