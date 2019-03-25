package de.saxsys.mvvmfx.scopes.scope_in_view;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.InjectViewModel;

public class ScopeInViewTestView implements FxmlView<ScopeInViewTestViewModel> {

	@InjectScope
	private TestScope scope;

	@InjectViewModel
	private ScopeInViewTestViewModel viewModel;

	public void initialize() {

	}
}
