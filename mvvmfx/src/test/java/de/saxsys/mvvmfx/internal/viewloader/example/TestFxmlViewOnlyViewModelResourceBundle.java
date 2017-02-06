package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

/**
 * A CodeBehind class that has no resourceBundles injected but has a ViewModel that injects
 * a ResourceBundle. This is needed to check that an exception is thrown when no resourcebundle is provided on loading.
 *
 * When the View itself injects a ResourceBundle that is used in the FXML file, an exception is
 * thrown by the FXMLLoader directly. However, it's possible that the resourceBundle is only used
 * in the ViewModel. In this case we have to throw an exception on our own.
 */
public class TestFxmlViewOnlyViewModelResourceBundle implements FxmlView<TestViewModelWithResourceBundle> {

	@InjectViewModel
	public TestViewModelWithResourceBundle viewModel;
}
