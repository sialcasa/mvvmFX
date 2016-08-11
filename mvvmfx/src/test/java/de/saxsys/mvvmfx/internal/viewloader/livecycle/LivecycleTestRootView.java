package de.saxsys.mvvmfx.internal.viewloader.livecycle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.StageLivecycle;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

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
