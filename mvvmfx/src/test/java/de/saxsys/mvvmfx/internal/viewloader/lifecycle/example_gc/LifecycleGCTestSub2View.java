package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LifecycleGCTestSub2View implements FxmlView<LifecycleGCTestSub2ViewModel> {

	@InjectViewModel
	public LifecycleGCTestSub2ViewModel viewModel;

	public void initialize() {

	}
}
