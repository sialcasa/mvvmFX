package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithWrongInjectedViewModel implements FxmlView<TestViewModelA> {
	
	// Wrong view model type
	@InjectViewModel
	private TestViewModelB viewModel;
	
}
