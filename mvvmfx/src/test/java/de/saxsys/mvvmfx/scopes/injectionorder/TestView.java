package de.saxsys.mvvmfx.scopes.injectionorder;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestView implements FxmlView<TestViewModel> {

	public static boolean wasScopeInjected = false;

	@InjectViewModel
	private TestViewModel viewModel;

	public void initialize() {
		wasScopeInjected = viewModel.scope != null;
	}
}
