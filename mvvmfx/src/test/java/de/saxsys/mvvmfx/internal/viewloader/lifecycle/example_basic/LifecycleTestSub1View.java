package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LifecycleTestSub1View implements FxmlView<LifecycleTestSub1ViewModel> {

	@InjectViewModel
	public LifecycleTestSub1ViewModel viewModel;

	public void initialize() {

	}
}
