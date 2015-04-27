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

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ObservableBooleanValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import eu.lestard.doc.Beta;

/**
 * A {@link Command} implementation that encapsulates an action ({@link Task<Void>}). It is possible to define that the
 * action should be executed in the background (not on the JavaFX thread) so that long running actions can be
 * implemented that aren't blocking the UI-Thread.
 * 
 * @author alexander.casall
 */
@Beta
public abstract class DelegateCommand extends Task<Void> implements Command {
	
	private boolean inBackground = false;
	protected final ReadOnlyBooleanWrapper executable = new ReadOnlyBooleanWrapper(true);
	protected ReadOnlyBooleanWrapper notExecutable;
	protected ReadOnlyBooleanWrapper notRunning;
	
	
	
	/**
	 * Creates a command without a condition about the executability. The command will perform in the thread which
	 * executes the {@link Command}.
	 * 
	 * @param action
	 *            which should execute
	 */
	public DelegateCommand() {
		this(null, false);
	}
	
	/**
	 * Creates a command without an condition about the executability. Pass a <code>true</code> to the
	 * <code>inBackground</code> parameter to run the {@link Command} in a background thread.
	 * 
	 * <b>IF YOU USE THE BACKGROUND THREAD: </b> Your provided action will perform in a background thread. If you
	 * manipulate data in your action, which will be propagated to the UI, use {@link Platform#runLater(Task<Void>)} for
	 * this manipulation, otherwise you get an Exception.
	 * 
	 * @param action
	 *            which should execute
	 * @param inBackground
	 *            defines whether the execution {@link #execute()} is performed in a background thread or not
	 */
	public DelegateCommand(boolean inBackground) {
		this(null, inBackground);
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
	public DelegateCommand(ObservableBooleanValue executableBinding) {
		this(executableBinding, false);
	}
	
	/**
	 * Creates a command with a condition about the executability by using the #executableBinding parameter. Pass a
	 * <code>true</code> to the #inBackground parameter to run the {@link Command} in a background thread.
	 * 
	 * <b>IF YOU USE THE BACKGROUND THREAD: </b> don't forget to return to the UI-thread by using {@link
	 * Platform#runLater(Task<Void>)}, otherwise you get an Exception.
	 * 
	 * @param action
	 *            which should execute
	 * @param executableBinding
	 *            which defines whether the {@link Command} can execute
	 * @param inBackground
	 *            defines whether the execution {@link #execute()} is performed in a background thread or not
	 */
	public DelegateCommand(ObservableBooleanValue executableBinding, boolean inBackground) {
		this.inBackground = inBackground;
		if (executableBinding != null) {
			executable.bind(runningProperty().not().and(executableBinding));
		}
		
	}
	
	/**
	 * The action which will called if the {@link #execute()} method is called.
	 * 
	 * @throws Exception
	 */
	protected abstract void action() throws Exception;
	
	/**
	 * @see de.saxsys.mvvmfx.utils.commands.Command#execute
	 */
	@Override
	public void execute() {
		if (!isExecutable()) {
			throw new RuntimeException("The execute()-method of the command was called while it wasn't executable.");
		} else {
			if (inBackground) {
				new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						return DelegateCommand.this;
					}
				}.start();
			} else {
				try {
					this.action();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public ReadOnlyBooleanProperty executableProperty() {
		return this.executable.getReadOnlyProperty();
	}
	
	
	@Override
	public boolean isExecutable() {
		return this.executableProperty().get();
	}
	
	@Override
	protected Void call() throws Exception {
		action();
		return null;
	}
	
	@Override
	public final ReadOnlyBooleanProperty notExecutableProperty() {
		if (notExecutable == null) {
			notExecutable = new ReadOnlyBooleanWrapper();
			notExecutable.bind(executableProperty().not());
		}
		return notExecutable.getReadOnlyProperty();
	}
	
	@Override
	public final boolean isNotExecutable() {
		return notExecutableProperty().get();
	}
	
	@Override
	public final ReadOnlyBooleanProperty notRunningProperty() {
		if (notRunning == null) {
			notRunning = new ReadOnlyBooleanWrapper();
			notRunning.bind(runningProperty().not());
		}
		return notRunning.getReadOnlyProperty();
	}
	
	@Override
	public final boolean isNotRunning() {
		return notRunningProperty().get();
	}
}
