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
import eu.lestard.doc.Beta;

/**
 * Basic implementation of a {@link Command}. It provides the {@link #executableProperty()} and
 * {@link #runningProperty()} implementations.
 * 
 * @author alexander.casall
 */
@Beta
public abstract class CommandBase implements Command {
	
	// Default true, so the Command can fire even if the user didn't provide an executable condition.
	protected final ReadOnlyBooleanWrapper executable = new ReadOnlyBooleanWrapper(true);
	protected final ReadOnlyBooleanWrapper running = new ReadOnlyBooleanWrapper(false);
	
	protected ReadOnlyBooleanWrapper notExecutable;
	protected ReadOnlyBooleanWrapper notRunning;
	
	@Override
	public final ReadOnlyBooleanProperty executableProperty() {
		return executable.getReadOnlyProperty();
	}
	
	@Override
	public final boolean isExecutable() {
		return executableProperty().get();
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
	public final ReadOnlyBooleanProperty runningProperty() {
		return running.getReadOnlyProperty();
	}
	
	@Override
	public final boolean isRunning() {
		return running.get();
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
	
	@Override
	public abstract void execute();
}
