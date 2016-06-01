package de.saxsys.mvvmfx.scopes.example4.views;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewModel;


public class DialogViewModel implements ViewModel {

	public static String scopeValueOnInitialization;

	@InjectScope
	DialogScope scope;

	public void initialize() {
		scopeValueOnInitialization = scope.someValue.getValue();
	}
}
