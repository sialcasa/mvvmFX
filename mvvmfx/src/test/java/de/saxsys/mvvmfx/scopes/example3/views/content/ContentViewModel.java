package de.saxsys.mvvmfx.scopes.example3.views.content;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.scopes.example3.Example3Scope;

public class ContentViewModel implements ViewModel {

	@InjectScope
	public Example3Scope scope;

}
