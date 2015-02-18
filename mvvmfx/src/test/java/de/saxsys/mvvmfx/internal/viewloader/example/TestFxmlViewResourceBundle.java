package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

import java.util.ResourceBundle;

public class TestFxmlViewResourceBundle implements FxmlView<TestViewModelWithResourceBundle> {
	
	public ResourceBundle resources;
	
	@InjectResourceBundle
	public ResourceBundle resourceBundle;
	
	@InjectViewModel
	TestViewModelWithResourceBundle viewModel;
	
}
