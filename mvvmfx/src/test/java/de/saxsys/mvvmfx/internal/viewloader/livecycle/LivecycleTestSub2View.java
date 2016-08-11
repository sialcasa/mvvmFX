package de.saxsys.mvvmfx.internal.viewloader.livecycle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LivecycleTestSub2View implements FxmlView<LivecycleTestSub2ViewModel> {

	@InjectViewModel
	public LivecycleTestSub2ViewModel viewModel;

	public void initialize() {

	}
}
