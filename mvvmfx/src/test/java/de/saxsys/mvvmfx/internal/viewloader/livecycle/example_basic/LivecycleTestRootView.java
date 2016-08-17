package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_basic;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class LivecycleTestRootView implements FxmlView<LivecycleTestRootViewModel> {

	@FXML
	public LivecycleTestSub1View sub1Controller;
	@FXML
	public LivecycleTestSub2View sub2Controller;

	@InjectViewModel
	private LivecycleTestRootViewModel viewModel;

	public void initialize() {

	}
}
