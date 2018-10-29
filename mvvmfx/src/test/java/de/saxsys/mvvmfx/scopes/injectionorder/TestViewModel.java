package de.saxsys.mvvmfx.scopes.injectionorder;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;

public class TestViewModel implements ViewModel {

	@InjectScope
	public TestScope scope;

}
