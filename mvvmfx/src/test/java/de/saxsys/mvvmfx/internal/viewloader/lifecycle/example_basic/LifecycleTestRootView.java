package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class LifecycleTestRootView implements FxmlView<LifecycleTestRootViewModel> {

	@FXML
	public LifecycleTestSub1View sub1Controller;
	@FXML
	public LifecycleTestSub2View sub2Controller;

	@InjectViewModel
	private LifecycleTestRootViewModel viewModel;

	public void initialize() {

	}
}
