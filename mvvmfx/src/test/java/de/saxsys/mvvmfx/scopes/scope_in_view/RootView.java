package de.saxsys.mvvmfx.scopes.scope_in_view;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class RootView implements FxmlView<RootViewModel> {

	@InjectViewModel
	private RootViewModel viewModel;

	public void initialize() {

	}
}
