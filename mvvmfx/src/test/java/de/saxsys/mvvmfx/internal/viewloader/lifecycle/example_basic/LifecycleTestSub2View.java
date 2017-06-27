package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LifecycleTestSub2View implements FxmlView<LifecycleTestSub2ViewModel> {

	@InjectViewModel
	public LifecycleTestSub2ViewModel viewModel;

	public void initialize() {

	}
}
