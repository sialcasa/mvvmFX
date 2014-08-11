package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;

public class TestViewB implements FxmlView<TestViewModelB> {
	
	@InjectViewModel
	public TestViewModelB viewModel;
	
	public boolean initializeWasCalled = false;
	
	public void initialize(){
		initializeWasCalled = true;
	}
}
