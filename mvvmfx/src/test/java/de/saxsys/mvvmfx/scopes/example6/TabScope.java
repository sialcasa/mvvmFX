package de.saxsys.mvvmfx.scopes.example6;

import de.saxsys.mvvmfx.Scope;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TabScope implements Scope {

	private StringProperty content = new SimpleStringProperty();

	public StringProperty contentProperty() {
		return content;
	}

}
