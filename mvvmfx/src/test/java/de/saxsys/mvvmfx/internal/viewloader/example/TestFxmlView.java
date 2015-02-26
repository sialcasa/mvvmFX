package de.saxsys.mvvmfx.internal.viewloader.example;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;


/**
 * This class is used as example View class that uses FXML.
 * 
 * @author manuel.mauky
 */
public class TestFxmlView implements FxmlView<TestViewModel>, Initializable {
	public URL url;
	public ResourceBundle resourceBundle;
	
	public static int instanceCounter = 0;
	
	@InjectViewModel
	private TestViewModel viewModel;
	
	public boolean viewModelWasNull = true;
	
	public TestFxmlView(){
		instanceCounter++;
	}
	
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
