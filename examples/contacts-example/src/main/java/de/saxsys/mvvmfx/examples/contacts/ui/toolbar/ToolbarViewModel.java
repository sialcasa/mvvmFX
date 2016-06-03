package de.saxsys.mvvmfx.examples.contacts.ui.toolbar;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;

import java.util.Collections;
import java.util.List;

public class ToolbarViewModel implements ViewModel {

	public List<Scope> getScopesForAddDialog() {
		return Collections.singletonList(new ContactDialogScope());
	}
}
