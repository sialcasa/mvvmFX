package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithViewModelWithMultipleInitializeMethods implements FxmlView<TestViewModelWithMultipleInitializeMethodsViewModel> {

	@InjectViewModel
	public TestViewModelWithMultipleInitializeMethodsViewModel viewModel;
}
