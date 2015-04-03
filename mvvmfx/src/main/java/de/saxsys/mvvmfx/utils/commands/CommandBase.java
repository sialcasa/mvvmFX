package de.saxsys.mvvmfx.utils.commands;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import eu.lestard.doc.Beta;

@Beta
public abstract class CommandBase implements Command {
	
	// Default true, so the Command can fire even if the user didn't provided an executable condition.
	protected final ReadOnlyBooleanWrapper executeable = new ReadOnlyBooleanWrapper(true);
	protected final ReadOnlyBooleanWrapper running = new ReadOnlyBooleanWrapper(false);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.saxsys.mvvmfx.utils.commands.Command#executeableProperty()
	 */
	@Override
	public final ReadOnlyBooleanProperty executeableProperty() {
		return this.executeable.getReadOnlyProperty();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.saxsys.mvvmfx.utils.commands.Command#isExecuteable()
	 */
	@Override
	public final boolean isExecuteable() {
		return this.executeableProperty().get();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.saxsys.mvvmfx.utils.commands.Command#executeableProperty()
	 */
	@Override
	public final ReadOnlyBooleanProperty runningProperty() {
		return this.running.getReadOnlyProperty();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.saxsys.mvvmfx.utils.commands.Command#isExecuteable()
	 */
	@Override
	public final boolean isRunning() {
		return this.running.get();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.saxsys.mvvmfx.utils.commands.Command#execute()
	 */
	@Override
	public abstract void execute();
}
