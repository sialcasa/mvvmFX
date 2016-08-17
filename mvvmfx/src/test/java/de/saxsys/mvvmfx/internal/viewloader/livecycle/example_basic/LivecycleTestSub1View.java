package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_basic;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LivecycleTestSub1View implements FxmlView<LivecycleTestSub1ViewModel> {

	@InjectViewModel
	public LivecycleTestSub1ViewModel viewModel;

	public void initialize() {

	}
}
