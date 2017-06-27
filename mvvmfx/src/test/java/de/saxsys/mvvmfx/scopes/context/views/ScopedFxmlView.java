package de.saxsys.mvvmfx.scopes.context.views;

import de.saxsys.mvvmfx.Context;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectContext;

public class ScopedFxmlView implements FxmlView<ScopedFxmlViewModel> {

	@InjectContext
	public Context context;

}
