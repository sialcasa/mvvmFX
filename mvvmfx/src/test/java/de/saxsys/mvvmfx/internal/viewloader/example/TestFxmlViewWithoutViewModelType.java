package de.saxsys.mvvmfx.internal.viewloader.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithoutViewModelType implements FxmlView, Initializable {
	
	// this injection point will be ignored as this view class doesn't define a ViewModelType
	@InjectViewModel
	public TestViewModel viewModel;
	
	public boolean wasInitialized = false;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		wasInitialized = true;
	}
}
