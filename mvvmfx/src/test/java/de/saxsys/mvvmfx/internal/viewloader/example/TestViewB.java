package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestViewB implements FxmlView<TestViewModelB> {
	
	@InjectViewModel
	public TestViewModelB viewModel;
	
	public boolean initializeWasCalled = false;
	
	public void initialize() {
		initializeWasCalled = true;
	}
}
