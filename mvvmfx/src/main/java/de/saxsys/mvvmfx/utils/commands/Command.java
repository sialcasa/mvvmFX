package de.saxsys.mvvmfx.utils.commands;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * A command combines an action with a condition. This can be used for example to provide an {@link #execute()}-action
 * which should perform on a button click. The button should be disabled, while the Command is not executable.
 * 
 * @author alexander.casall
 *
 */
public interface Command {
	
	/**
	 * Executes the Command.
	 */
	void execute();
	
	/**
	 * @see #isExecuteable()
	 */
	ReadOnlyBooleanProperty executeableProperty();
	
	/**
	 * @return whether the {@link Command} can execute
	 */
	boolean isExecuteable();
	
}