package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithViewModelWithNonValidInitializeMethodViewWithArguments
		implements FxmlView<TestViewModelWithNonValidInitializeMethodWithArguments> {

	@InjectViewModel
	private TestViewModelWithNonValidInitializeMethodWithArguments viewModel;

	public void initialize() {

	}
}
