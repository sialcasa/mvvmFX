package de.saxsys.mvvmfx.utils.commands;

import javafx.concurrent.Task;

public abstract class Action extends Task<Void> {
	
	@Override
	protected Void call() throws Exception {
		action();
		return null;
	}
	
	protected abstract void action() throws Exception;
	
}
