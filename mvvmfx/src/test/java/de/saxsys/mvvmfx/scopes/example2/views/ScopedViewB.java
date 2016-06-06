package de.saxsys.mvvmfx.scopes.example2.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ScopedViewB implements FxmlView<ScopedViewModelB> {

	@InjectViewModel
	public ScopedViewModelB viewModel;

}
