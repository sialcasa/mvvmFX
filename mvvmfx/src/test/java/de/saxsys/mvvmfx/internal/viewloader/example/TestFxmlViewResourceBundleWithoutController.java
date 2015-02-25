package de.saxsys.mvvmfx.internal.viewloader.example;

import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewResourceBundleWithoutController implements FxmlView<TestViewModelWithResourceBundle> {
	
	public ResourceBundle resources;
	
	@InjectResourceBundle
	public ResourceBundle resourceBundle;
	
	@InjectViewModel
	TestViewModelWithResourceBundle viewModel;
	
}
