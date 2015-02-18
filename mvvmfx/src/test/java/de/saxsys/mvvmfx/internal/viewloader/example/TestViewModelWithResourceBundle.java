package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.ViewModel;

import java.util.ResourceBundle;

public class TestViewModelWithResourceBundle implements ViewModel {
	
	@InjectResourceBundle
	public ResourceBundle resourceBundle;
	
}
