package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewMultipleViewModels implements FxmlView<TestViewModel> {
	@InjectViewModel
	public TestViewModel viewModel1;
	
	@InjectViewModel
	public TestViewModel viewModel2;
}
