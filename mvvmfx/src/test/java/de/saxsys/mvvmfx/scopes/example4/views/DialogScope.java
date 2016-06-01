package de.saxsys.mvvmfx.scopes.example4.views;

import de.saxsys.mvvmfx.Scope;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DialogScope implements Scope {

	StringProperty someValue = new SimpleStringProperty();

}
