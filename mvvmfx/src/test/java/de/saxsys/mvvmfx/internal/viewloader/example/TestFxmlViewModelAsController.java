package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlPath;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;

public class TestFxmlViewModelAsController implements FxmlView<TestFxmlViewModelAsControllerViewModel> {

	@InjectViewModel
	TestFxmlViewModelAsControllerViewModel viewModel;

}
