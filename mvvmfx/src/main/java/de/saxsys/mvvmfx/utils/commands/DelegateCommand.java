package de.saxsys.mvvmfx.utils.commands;

import javafx.beans.value.ObservableBooleanValue;
import eu.lestard.doc.Beta;

@Beta
public class DelegateCommand extends CommandBase {
	
	private final Runnable action;
	
	/**
	 * 
	 * @param action
	 */
	public DelegateCommand(Runnable action) {
		this.action = action;
	}
	
	/**
	 * 
	 * @param action
	 * @param executableBinding
	 */
	public DelegateCommand(Runnable action, ObservableBooleanValue executableBinding) {
		this.action = action;
		executeable.bind(executableBinding);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.saxsys.mvvmfx.utils.commands.Command#fire()
	 */
	@Override
	public void execute() {
		if (!isExecuteable()) {
			throw new RuntimeException("Not executable");
		} else {
			running.set(true);
			action.run();
			running.set(false);
		}
	}
	
}
