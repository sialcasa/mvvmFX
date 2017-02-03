package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectResourceBundle;

import java.util.ResourceBundle;

/**
 * Reproduces the bug <a href="https://github.com/sialcasa/mvvmFX/issues/469">#469</a>.
 * When a View wasn't injecting the ViewModel then in the ViewModel the resourceBundle wasn't injected.
 */
public class TestFxmlViewResourceBundleWithoutViewModelInjectedView
		implements FxmlView<TestViewModelWithResourceBundle> {

	public ResourceBundle resources;

	@InjectResourceBundle
	public ResourceBundle resourceBundle;

}
