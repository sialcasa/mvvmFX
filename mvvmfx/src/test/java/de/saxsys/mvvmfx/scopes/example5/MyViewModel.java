package de.saxsys.mvvmfx.scopes.example5;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;

public class MyViewModel implements ViewModel {

	@InjectScope
	public Example5Scope scope;

}
