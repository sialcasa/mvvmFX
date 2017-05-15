package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithViewModelWithMultipleInitializeAnnotations implements FxmlView<TestViewModelWithMultipleInitializeAnnotations> {

	@InjectViewModel
	private TestViewModelWithMultipleInitializeAnnotations viewModel;

}
