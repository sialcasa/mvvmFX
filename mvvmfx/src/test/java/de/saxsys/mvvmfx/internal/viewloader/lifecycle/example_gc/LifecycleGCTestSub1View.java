package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LifecycleGCTestSub1View implements FxmlView<LifecycleGCTestSub1ViewModel> {

	@InjectViewModel
	public LifecycleGCTestSub1ViewModel viewModel;

	public void initialize() {

	}
}
