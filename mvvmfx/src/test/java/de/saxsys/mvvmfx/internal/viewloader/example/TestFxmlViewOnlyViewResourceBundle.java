package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

/**
 * A CodeBehind class that has a resourceBundle injected but the ViewModel not.
 * This is needed to check that an exception is thrown when no resourceBundle is provided on loading.
 *
 * When the viewModel gets a resourceBundle injected an exception is thrown. However, to verify
 * that an exception is also thrown the only Codebehind needs the resourceBundle we use
 * this class for this use case.
 */
public class TestFxmlViewOnlyViewResourceBundle implements FxmlView<TestViewModelWithResourceBundle> {

	@InjectViewModel
	public TestViewModelWithResourceBundle viewModel;
}
