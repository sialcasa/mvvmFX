package de.saxsys.mvvmfx.scopes.example6;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.StringProperty;

public class TabViewModel implements ViewModel {

	@InjectScope
	private TabScope scope;

	public StringProperty labelTextProperty() {
		return scope.contentProperty();
	}

}
