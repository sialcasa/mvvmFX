package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class LifecycleGCTestRootView implements FxmlView<LifecycleGCTestRootViewModel> {

	@FXML
	public LifecycleGCTestSub1View sub1Controller;
	@FXML
	public LifecycleGCTestSub2View sub2Controller;

	@InjectViewModel
	private LifecycleGCTestRootViewModel viewModel;

	public void initialize() {

	}
}
