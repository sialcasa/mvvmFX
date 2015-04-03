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

/**
 * The {@link Command} encapsulates logic in the {@link #execute()} method which will called later. This can be used for
 * example to provide an {@link #execute()}-action which should perform on a button click. In addition it is possible to
 * add the an information, whether the {@link Command} is {@link #isExecuteable()}.
 * 
 * @author alexander.casall
 *
 */
public interface Command {
	
	/**
	 * Defines the method to be called when the command is invoked.
	 */
	void execute();
	
	
	
	/**
	 * Determines whether the command can execute in its current state.
	 * 
	 * @return whether the {@link Command} can execute
	 */
	boolean isExecuteable();
	
	/**
	 * @see #isExecuteable()
	 */
	ReadOnlyBooleanProperty executeableProperty();
	
	
	/**
	 * Signals whether the command is currently executing. It can takes some time.
	 * 
	 * @return whether the {@link Command} is running
	 */
	boolean isRunning();
	
	/**
	 * @see #isRunning()
	 */
	ReadOnlyBooleanProperty runningProperty();
	
}