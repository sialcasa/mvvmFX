/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.utils.commands;

import javafx.application.Platform;
import javafx.beans.value.ObservableBooleanValue;
import eu.lestard.doc.Beta;

/**
 * The {@link DelegateCommand} encapsulates logic in the {@link #execute()} method which will called later. This can be
 * used for example to provide an {@link #execute()}-action which should perform on a button click. In addition it is
 * possible to add the an information, whether the {@link Command} is {@link #isExecuteable()} - this is optional. The
 * execution can happen asynchronously - this is optional.
 * 
 * @author sialcasa
 */
@Beta
public class DelegateCommand extends CommandBase {
	
	private final Runnable action;
	private boolean inBackground = false;
	
	/**
	 * Creates a command without an condition about the executability. The command will perform in the thread which
	 * executes the {@link Command}.
	 * 
	 * @param action
	 *            which should execute
	 */
	public DelegateCommand(Runnable action) {
		this(action, null);
	}
	
	/**
	 * Creates a command without an condition about the executability. Pass a <code>true</code> to the #inBackground
	 * parameter to run the {@link Command} in a background thread.
	 * 
	 * <b>IF YOU USE THE BACKGROUND THREAD: </b> don't forget to return to the UI-thread by using
	 * {@link Platform#runLater(Runnable)}, otherwise you get an Exception.
	 * 
	 * @param action
	 *            which should execute
	 * @param inBackground
	 *            defines whether the execution {@link #execute()} is performed in a background thread or not
	 */
	public DelegateCommand(Runnable action, boolean inBackground) {
		this(action, null, inBackground);
	}
	
	/**
	 * Creates a command with a condition about the executability by using the #executableBinding parameter. The command
	 * will perform in the thread which executes the {@link Command}.
	 * 
	 * @param action
	 *            which should execute
	 * @param executableBinding
	 *            which defines whether the {@link Command} can execute
	 */
	public DelegateCommand(Runnable action, ObservableBooleanValue executableBinding) {
		this(action, executableBinding, false);
	}
	
	/**
	 * Creates a command with a condition about the executability by using the #executableBinding parameter. Pass a
	 * <code>true</code> to the #inBackground parameter to run the {@link Command} in a background thread.
	 * 
	 * <b>IF YOU USE THE BACKGROUND THREAD: </b> don't forget to return to the UI-thread by using
	 * {@link Platform#runLater(Runnable)}, otherwise you get an Exception.
	 * 
	 * @param action
	 *            which should execute
	 * @param executableBinding
	 *            which defines whether the {@link Command} can execute
	 * @param inBackground
	 *            defines whether the execution {@link #execute()} is performed in a background thread or not
	 */
	public DelegateCommand(Runnable action, ObservableBooleanValue executableBinding, boolean inBackground) {
		this.action = action;
		this.inBackground = inBackground;
		if (executableBinding != null) {
			executeable.bind(runningProperty().not().and(executableBinding));
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
