package de.saxsys.jfx.mvvm.viewloader.example;

import javafx.scene.layout.VBox;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;

public class TestFxmlViewFxRoot extends VBox implements FxmlView<TestViewModel> {
	
	@InjectViewModel
	public TestViewModel viewModel;
	
	public boolean viewModelWasNull = true;
	
	public void initialize() {
		viewModelWasNull = viewModel == null;
	}
}
