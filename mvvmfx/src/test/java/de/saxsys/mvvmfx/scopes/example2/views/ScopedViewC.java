package de.saxsys.mvvmfx.scopes.example2.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ScopedViewC implements FxmlView<ScopedViewModelC> {

	@InjectViewModel
	public ScopedViewModelC viewModel;
}
