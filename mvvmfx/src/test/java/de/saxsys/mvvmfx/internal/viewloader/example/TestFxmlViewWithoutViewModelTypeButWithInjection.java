package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithoutViewModelTypeButWithInjection implements FxmlView {
	
	@InjectViewModel
	public TestViewModel viewModel;
	
}
