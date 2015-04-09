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
 * A {@link Command} implementation that encapsulates an action ({@link Runnable}). It is possible to define that the
 * action should be executed in the background (not on the JavaFX thread) so that long running actions can be
 * implemented that aren't blocking the ui thread.
 * 
 * @author alexander.casall
 */
@Beta
public class DelegateCommand extends CommandBase {
	
	private final Runnable action;
	private boolean inBackground = false;
	
	/**
	 * Creates a command without a condition about the executability. The command will perform in the thread which
	 * executes the {@link Command}.
	 * 
	 * @param action
	 *            which should execute
	 */
	public DelegateCommand(Runnable action) {
		this(action, null, false);
	}
	
	/**
	 * Creates a command without an condition about the executability. Pass a <code>true</code> to the
	 * <code>inBackground</code> parameter to run the {@link Command} in a background thread.
	 * 
	 * <b>IF YOU USE THE BACKGROUND THREAD: </b> Your provided action will perform in a background thread. If you
	 * manipulate data in your action, which will be propagated to the UI, use {@link Platform#runLater(Runnable)} for
	 * this manipulation, otherwise you get an Exception.
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
			executable.bind(runningProperty().not().and(executableBinding));
		}
	}
	
	/**
	 * @see de.saxsys.mvvmfx.utils.commands.Command#execute
	 */
	@Override
	public final void execute() {
		
		final boolean callerOnUIThread = Platform.isFxApplicationThread();
		
		if (!isExecutable()) {
			throw new RuntimeException("The execute()-method of the command was called while it wasn't executable.");
		} else {
			running.set(true);
			if (inBackground) {
				new Thread(() -> {
					action.run();
					if (callerOnUIThread) {
						Platform.runLater(() -> running.set(false));
					} else {
						running.set(false);
					}
				}).start();
			} else {
				action.run();
				running.set(false);
			}
		}
	}
	
}
