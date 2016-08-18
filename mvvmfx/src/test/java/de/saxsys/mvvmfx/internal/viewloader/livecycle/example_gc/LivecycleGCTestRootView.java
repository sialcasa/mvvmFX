package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class LivecycleGCTestRootView implements FxmlView<LivecycleGCTestRootViewModel> {

	@FXML
	public LivecycleGCTestSub1View sub1Controller;
	@FXML
	public LivecycleGCTestSub2View sub2Controller;

	@InjectViewModel
	private LivecycleGCTestRootViewModel viewModel;

	public void initialize() {

	}
}
