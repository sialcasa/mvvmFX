package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LivecycleGCTestSub1View implements FxmlView<LivecycleGCTestSub1ViewModel> {

	@InjectViewModel
	public LivecycleGCTestSub1ViewModel viewModel;

	public void initialize() {

	}
}
