package de.saxsys.jfx.mvvm.viewloader.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;


/**
 * This class is used as example View class that uses FXML.
 * 
 * @author manuel.mauky
 */
public class TestFxmlView implements FxmlView<TestViewModel>, Initializable {
	public URL url;
	public ResourceBundle resourceBundle;
	
	@InjectViewModel
	private TestViewModel viewModel;
	
	public boolean viewModelWasNull = true;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.url = url;
		this.resourceBundle = resourceBundle;
		
		viewModelWasNull = viewModel == null;
	}
	
	public TestViewModel getViewModel(){
		return viewModel;
	}
	
}
