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
import javafx.beans.property.ReadOnlyDoubleProperty;
import eu.lestard.doc.Beta;

/**
 * The {@link Command} encapsulates logic in the {@link #execute()} method which will be called later. This can be used
 * for example to provide an {@link #execute()}-action which should perform on a button click.
 * <p>
 * In addition it is possible to add the information on whether the {@link Command} can be executed at the moment or not
 * ({@link #isExecutable()}).
 * 
 * @author alexander.casall
 *
 */
@Beta
public interface Command {
	
	/**
	 * This method will be called when the command is invoked. This has to get called from FX Thread
	 */
	void execute();
	
	
	
	/**
	 * Determines whether the command can be executed in it's current state.
	 * 
	 * @return <code>true</code> if the {@link Command} can executed, otherwise <code>false</code>.
	 */
	boolean isExecutable();
	
	/**
	 * @see #isExecutable()
	 */
	ReadOnlyBooleanProperty executableProperty();
	
	/**
	 * Determines whether the command can not execute in it's current state.
	 * 
	 * @return <code>true</code> if the {@link Command} can not execute, otherwise <code>false</code>.
	 */
	boolean isNotExecutable();
	
	/**
	 * @see #isNotExecutable()
	 */
	ReadOnlyBooleanProperty notExecutableProperty();
	
	
	/**
	 * Signals whether the command is currently executing. This can be useful especially for commands that are executed
	 * asynchronously.
	 * 
	 * @return <code>true</code> if the {@link Command} is running, otherwise <code>false</code>.
	 */
	boolean isRunning();
	
	/**
	 * @see #isRunning()
	 */
	ReadOnlyBooleanProperty runningProperty();
	
	/**
	 * Signals whether the command is currently not executing. This can be useful especially for commands that are
	 * executed asynchronously.
	 * 
	 * @return <code>true</code> if the {@link Command} is not running, otherwise <code>false</code>.
	 */
	boolean isNotRunning();
	
	/**
	 * @see #isNotRunning()
	 */
	ReadOnlyBooleanProperty notRunningProperty();
	
	/**
	 * Gets a double between 0.0 and 1.0 which represents the progress.
	 * 
	 * @return progress
	 */
	double getProgress();
	
	/**
	 * @see #getProgress()
	 */
	ReadOnlyDoubleProperty progressProperty();
	
	
	
}