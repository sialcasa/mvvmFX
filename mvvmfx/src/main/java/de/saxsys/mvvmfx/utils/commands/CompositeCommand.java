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

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import eu.lestard.doc.Beta;

import java.util.concurrent.Callable;
import java.util.function.Function;

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
	
	ReadOnlyDoubleWrapper progress = new ReadOnlyDoubleWrapper();
	
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
				if (registeredCommands.isEmpty()) {
					executable.unbind();
					running.unbind();
					progress.unbind();
				} else {
					BooleanBinding executableBinding = constantOf(true);
					BooleanBinding runningBinding = constantOf(false);

					for (Command registeredCommand : registeredCommands) {
						ReadOnlyBooleanProperty currentExecutable = registeredCommand.executableProperty();
						ReadOnlyBooleanProperty currentRunning = registeredCommand.runningProperty();
						executableBinding = executableBinding.and(currentExecutable);
						runningBinding = runningBinding.or(currentRunning);
					}
					executable.bind(executableBinding);
					running.bind(runningBinding);

					initProgressBinding();
				}
			}
		});
	}
	
	private void initProgressBinding() {
		DoubleExpression tmp = constantOf(0);

		for (Command command : registeredCommands) {
			final ReadOnlyDoubleProperty progressProperty = command.progressProperty();

			/**
			 * When the progress of a command is "undefined", the progress property has a value of -1.
			 * But in our use case we like to have a value of 0 in this case. 
			 * Therefore we create a custom binding here.
			 */
			final DoubleBinding normalizedProgress = Bindings
					.createDoubleBinding(() -> (progressProperty.get() == -1) ? 0.0 : progressProperty.get(),
							progressProperty);

			tmp = tmp.add(normalizedProgress);
		}
		
		int divisor = registeredCommands.isEmpty() ? 1 : registeredCommands.size();
		progress.bind(Bindings.divide(tmp, divisor));
	}
	
	@Override
	public void execute() {
		if (!isExecutable()) {
			throw new RuntimeException("Not executable");
		} else {
			if (!registeredCommands.isEmpty()) {
				registeredCommands.forEach(t -> t.execute());
			}
		}
	}
	
	
	@Override
	public double getProgress() {
		return progressProperty().get();
	}
	
	@Override
	public ReadOnlyDoubleProperty progressProperty() {
		return progress;
	}
	
	private BooleanBinding constantOf(boolean defaultValue) {
		return new BooleanBinding() {
			@Override
			protected boolean computeValue() {
				return defaultValue;
			}
		};
	}
	
	private DoubleBinding constantOf(double defaultValue) {
		return new DoubleBinding() {
			@Override
			protected double computeValue() {
				return defaultValue;
			}
		};
	}
	
}
