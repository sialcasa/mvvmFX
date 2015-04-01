package de.saxsys.mvvmfx.utils.commands;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ObservableBooleanValue;

public class Command {
	
	// Default True, wenn kein binding angegeben ist
	private final ReadOnlyBooleanWrapper executeable = new ReadOnlyBooleanWrapper(true);
	private final Runnable action;
	
	public Command(Runnable action) {
		this.action = action;
	}
	
	public Command(Runnable action, ObservableBooleanValue executableBinding) {
		this.action = action;
		executeable.bind(executableBinding);
	}
	
	public void fire() {
		if (!isExecuteable()) {
			throw new RuntimeException("Not executable");
		} else {
			action.run();
		}
	}
	
	public final ReadOnlyBooleanProperty executeableProperty() {
		return this.executeable.getReadOnlyProperty();
	}
	
	public final boolean isExecuteable() {
		return this.executeableProperty().get();
	}
	
}
