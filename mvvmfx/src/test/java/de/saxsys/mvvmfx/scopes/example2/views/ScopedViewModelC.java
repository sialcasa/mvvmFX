package de.saxsys.mvvmfx.scopes.example2.views;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.scopes.example2.Example2Scope1;


public class ScopedViewModelC implements ViewModel {


	@InjectScope
	public Example2Scope1 scope;
}
