package de.saxsys.mvvmfx.internal.viewloader.example;

import javafx.scene.layout.VBox;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewFxRoot extends VBox implements FxmlView<TestViewModel> {
	
	@InjectViewModel
	public TestViewModel viewModel;
	
	public boolean viewModelWasNull = true;
	
	public void initialize() {
		viewModelWasNull = viewModel == null;
	}
}
