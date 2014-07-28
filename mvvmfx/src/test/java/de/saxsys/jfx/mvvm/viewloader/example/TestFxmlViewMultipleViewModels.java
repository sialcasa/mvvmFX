package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;

public class TestFxmlViewMultipleViewModels implements FxmlView<TestViewModel> {
	@InjectViewModel
	public TestViewModel viewModel1;
	
	@InjectViewModel
	public TestViewModel viewModel2;
}
