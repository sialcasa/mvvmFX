package de.saxsys.jfx.mvvm.viewloader.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;

public class TestFxmlViewWithoutViewModel implements FxmlView, Initializable {
	
	// this injection point will be ignored as this view class doesn't define a ViewModelType
	@InjectViewModel
	public TestViewModel viewModel;
	
	public boolean wasInitialized = false;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		wasInitialized = true;
	}
}
