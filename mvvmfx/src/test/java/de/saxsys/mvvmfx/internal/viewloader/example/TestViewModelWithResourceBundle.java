package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.ViewModel;

import java.util.ResourceBundle;

public class TestViewModelWithResourceBundle implements ViewModel {
	public static boolean wasInitialized = false;
	public static boolean resourceBundleWasAvailableAtInitialize = false;
	
	
	@InjectResourceBundle
	public ResourceBundle resourceBundle;
	
	public void initialize() {
		wasInitialized = true;
		resourceBundleWasAvailableAtInitialize = resourceBundle != null;
	}
	
}
