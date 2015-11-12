package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.Scope;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TestScope implements Scope {
	
	public BooleanProperty someProperty = new SimpleBooleanProperty();
	
}
