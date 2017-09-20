package de.saxsys.mvvmfx.testingutils;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class JfxToolkitExtension implements BeforeAllCallback {
	@Override public void beforeAll(ExtensionContext extensionContext) throws Exception {
		new JFXPanel();
	}
}
