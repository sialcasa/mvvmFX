package de.saxsys.mvvmfx.scopes.example2.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ScopedViewA implements FxmlView<ScopedViewModelA> {
	public ScopedViewB subviewBController;
	public ScopedViewC subviewCController;

	@InjectViewModel
	public ScopedViewModelA viewModelA;
}
