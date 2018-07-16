package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithViewModelWithNonValidInitializeMethodViewWithBadReturnType
		implements FxmlView<TestViewModelWithNonValidInitializeMethodWithBadReturnType> {

	@InjectViewModel
	private TestViewModelWithNonValidInitializeMethodWithBadReturnType viewModel;

	public void initialize() {

	}
}
