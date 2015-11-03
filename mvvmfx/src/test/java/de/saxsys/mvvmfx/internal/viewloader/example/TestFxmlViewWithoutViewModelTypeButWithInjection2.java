package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewModel;

public class TestFxmlViewWithoutViewModelTypeButWithInjection2 implements FxmlView {
	
	@InjectViewModel
	public ViewModel viewModel;
	
}
