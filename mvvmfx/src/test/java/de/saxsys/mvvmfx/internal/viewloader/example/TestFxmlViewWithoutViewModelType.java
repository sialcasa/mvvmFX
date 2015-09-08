package de.saxsys.mvvmfx.internal.viewloader.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithoutViewModelType implements FxmlView, Initializable {
	
	public boolean wasInitialized = false;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		wasInitialized = true;
	}
}
