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

import eu.lestard.doc.Beta;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * CompositeCommand is an aggregation of other commands - a list of {@link Command} references internally.
 * <p>
 * It allows you to hook up multiple command targets to a single root command that itself can be hooked up to a command
 * source such as a button or menu item.
 * <p>
 * The {@link CompositeCommand} can hold references to any {@link Command object} but typically you will use it in
 * conjunction with {@link DelegateCommand}s.
 * 
 * <p>
 * When the {@link CompositeCommand#execute} method is invoked it will invoke the {@link Command#execute} method on each
 * of the child commands.
 * 
 * <p>
 * 
 * When {@link #isExecutable()} is called to determine whether the command is enabled, it polls its child commands for
 * their result from {@link #isExecutable()}.
 * 
 * @author alexander.casall
 *
 */
@Beta
public class CompositeCommand extends CommandBase {
	
	private final ObservableList<Command> registeredCommands = FXCollections.observableArrayList();
	
	/**
	 * Creates a {@link CompositeCommand} with given commands.
	 * 
	 * @param commands
	 *            to aggregate
	 */
	public CompositeCommand(Command... commands) {
		initRegisteredCommandsListener();
		
		this.registeredCommands.addAll(commands);
	}
	
	/**
	 * Registers a new {@link Command} for aggregation.
	 * 
	 * @param command
	 *            to register
	 */
	public void register(Command command) {
		registeredCommands.add(command);
	}
	
	/**
	 * Unregisters a {@link Command} from aggregation.
	 * 
	 * @param command
	 *            to unregister
	 */
	public void unregister(Command command) {
		registeredCommands.remove(command);
	}
	
	private void initRegisteredCommandsListener() {
		this.registeredCommands.addListener((ListChangeListener<Command>) c -> {
			while (c.next()) {
				if(registeredCommands.isEmpty()) {
					executable.unbind();
					running.unbind();
				} else {
					BooleanBinding executableBinding = null;
					BooleanBinding runningBinding = null;

					for (int i = 0; i < registeredCommands.size(); i++) {
						ReadOnlyBooleanProperty currentExecutable = registeredCommands.get(i).executableProperty();
						ReadOnlyBooleanProperty currentRunning = registeredCommands.get(i).runningProperty();

						if (i == 0) {
							executableBinding = currentExecutable.and(currentExecutable);
							runningBinding = currentRunning.or(currentRunning);
						} else {
							executableBinding = executableBinding.and(currentExecutable);
							runningBinding = runningBinding.or(currentRunning);
						}
					}
					executable.bind(executableBinding);
					running.bind(runningBinding);
				}
			}
		});
	}
	
	@Override
	public void execute() {
		if (!isExecutable()) {
			throw new RuntimeException("Not executable");
		} else {
			registeredCommands.forEach(t -> t.execute());
		}
	}
	
}
