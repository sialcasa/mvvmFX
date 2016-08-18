package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LivecycleGCTestSub2View implements FxmlView<LivecycleGCTestSub2ViewModel> {

	@InjectViewModel
	public LivecycleGCTestSub2ViewModel viewModel;

	public void initialize() {

	}
}
