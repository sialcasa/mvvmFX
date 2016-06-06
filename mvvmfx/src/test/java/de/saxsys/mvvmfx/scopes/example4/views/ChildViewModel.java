package de.saxsys.mvvmfx.scopes.example4.views;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeProvider;
import de.saxsys.mvvmfx.ViewModel;

@ScopeProvider(scopes = DialogScope.class)
public class ChildViewModel implements ViewModel {

	public static final String OPEN_DIALOG_MESSAGE = "ChildViewModel.OPEN_DIALOG_MESSAGE";


	@InjectScope
	public DialogScope dialogScope;

	public void openDialog() {
		dialogScope.someValue.set("something");

		publish(OPEN_DIALOG_MESSAGE);
	}
}
