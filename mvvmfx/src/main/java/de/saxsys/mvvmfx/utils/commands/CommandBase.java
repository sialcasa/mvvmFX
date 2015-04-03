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
 * Basic implementation of a {@link Command}. It provides the {@link #executeableProperty()} and
 * {@link #runningProperty()} implementations.
 * 
 * @author sialcasa
 */
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
