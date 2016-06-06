package de.saxsys.mvvmfx.scopes.example3.views.menu;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.scopes.example3.Example3Scope;

public class MenuViewModel implements ViewModel {

	@InjectScope
	public Example3Scope scope;

}
