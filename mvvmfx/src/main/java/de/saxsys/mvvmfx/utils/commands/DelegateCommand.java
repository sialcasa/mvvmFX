package de.saxsys.mvvmfx.utils.commands;

import javafx.beans.value.ObservableBooleanValue;
import eu.lestard.doc.Beta;

@Beta
public class DelegateCommand extends CommandBase {
	
	private final Runnable action;
	boolean inBackground = false;
	
	public DelegateCommand(Runnable action) {
		this(action, null);
	}
	
	public DelegateCommand(Runnable action, boolean inBackground) {
		this(action, null, inBackground);
	}
	
	public DelegateCommand(Runnable action, ObservableBooleanValue executableBinding) {
		this(action, executableBinding, false);
	}
	
	public DelegateCommand(Runnable action, ObservableBooleanValue executableBinding, boolean inBackground) {
		this.action = action;
		this.inBackground = inBackground;
		if (executableBinding != null) {
			executeable.bind(executableBinding);
		}
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
			if (inBackground) {
				new Thread(() -> {
					action.run();
					running.set(false);
				}).start();
			} else {
				action.run();
				running.set(false);
			}
		}
	}
	
	
}
