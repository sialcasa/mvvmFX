package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;

public class TestFxmlViewWithMissingController implements FxmlView<TestViewModel> {
	
	@InjectViewModel
	public TestViewModel viewModel;
}
