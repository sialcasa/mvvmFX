package de.saxsys.mvvmfx.scopes.example1;

import de.saxsys.mvvmfx.Scope;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Example1Scope1 implements Scope {

	public BooleanProperty someProperty = new SimpleBooleanProperty();

}
