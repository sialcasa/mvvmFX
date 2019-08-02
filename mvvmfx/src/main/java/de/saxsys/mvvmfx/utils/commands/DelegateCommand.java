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

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.lestard.doc.Beta;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * A {@link Command} implementation of a {@link Service<Void>} that encapsulates an {@link Action} ({@link Task<Void>})
 * which can be called from the UI - for example after a button click. If the {@link Action} is a long running
 * operation, which would block your UI, you can pass a parameter to perform the {@link Action} in a background thread.
 * You can bind to the {@link #isRunning()} property while the action is executing. This can be used for a loading
 * indication in the UI.
 * 
 * @author alexander.casall
 */
@Beta
public class DelegateCommand extends Service<Void> implements Command {
	
	protected final Supplier<Action> actionSupplier;
	protected boolean inBackground = false;
	protected final ReadOnlyBooleanWrapper executable = new ReadOnlyBooleanWrapper(true);
	protected ReadOnlyBooleanWrapper notExecutable;
	protected ReadOnlyBooleanWrapper notRunning;
	private Property<Throwable> writableExceptionProperty;
	
	Logger LOG = LoggerFactory.getLogger(DelegateCommand.class);
	
	/**
	 * Creates a command without a condition about the executability.
	 * 
	 * @param actionSupplier
	 *            a function that returns a new Action which should be executed
	 */
	public DelegateCommand(final Supplier<Action> actionSupplier) {
		this(actionSupplier, false);
	}
	
	/**
	 * Creates a command without an condition about the executability. Pass a <code>true</code> to the
	 * <code>inBackground</code> parameter to run the {@link Command} in a background thread.
	 * 
	 * <b>IF YOU USE THE BACKGROUND THREAD: </b> Your provided action will perform in a background thread. If you
	 * manipulate data in your action, which will be propagated to the UI, use {@link Platform#runLater(Runnable)} for
	 * this manipulation, otherwise you get an Exception by JavaFX.
	 *
	 * @param actionSupplier
	 *            a function that returns a new Action which should be executed
	 * @param inBackground
	 *            defines whether the execution {@link #execute()} is performed in a background thread or not
	 */
	public DelegateCommand(final Supplier<Action> actionSupplier, boolean inBackground) {
		this(actionSupplier, null, inBackground);
	}
	
	/**
	 * Creates a command with a condition about the executability by using the 'executableObservable' parameter.
	 *
	 * @param actionSupplier
	 *            a function that returns a new Action which should be executed
	 * @param executableObservable
	 *            which defines whether the {@link Command} can execute
	 */
	public DelegateCommand(final Supplier<Action> actionSupplier, ObservableValue<Boolean> executableObservable) {
		this(actionSupplier, executableObservable, false);
	}
	
	/**
	 * Creates a command with a condition about the executability by using the 'executableObservable' parameter. Pass a
	 * <code>true</code> to the #inBackground parameter to run the {@link Command} in a background thread.
	 * 
	 * <b>IF YOU USE THE BACKGROUND THREAD: </b> don't forget to return to the UI-thread by using
	 * {@link Platform#runLater(Runnable)}, otherwise you get an Exception.
	 *
	 * @param actionSupplier
	 *            a function that returns a new Action which should be executed
	 * @param executableObservable
	 *            which defines whether the {@link Command} can execute
	 * @param inBackground
	 *            defines whether the execution {@link #execute()} is performed in a background thread or not
	 */
	public DelegateCommand(final Supplier<Action> actionSupplier, ObservableValue<Boolean> executableObservable,
			boolean inBackground) {
		this.actionSupplier = actionSupplier;
		this.inBackground = inBackground;
		if (executableObservable != null) {
			executable.bind(Bindings.createBooleanBinding(() -> {
				final boolean isRunning = runningProperty().get();
				final boolean isExecutable = executableObservable.getValue();
				
				return !isRunning && isExecutable;
			}, runningProperty(), executableObservable));
		}
		
	}
	
	/**
	 * @see de.saxsys.mvvmfx.utils.commands.Command#execute
	 */
	@Override
	public void execute() {
		if (!isExecutable()) {
			throw new RuntimeException("The execute()-method of the command was called while it wasn't executable.");
		} else {
			if (inBackground) {
				super.restart();
			} else {
				// When the command is not executed in background, we have to imitate a service execution, so the
				// service statemachine provides the
				// correct service state to the command.
				callActionAndSynthesizeServiceRun();
			}
		}
	}
	
	/**
	 * For internal purposes we need to change the state property of the service.
	 */
	private ObjectProperty<State> getStateObjectPropertyReadable() {
		return (ObjectProperty<State>) stateProperty();
	}

	/**
	 * In synchronous mode we need to simulate the lifecycle of the service and therefore manipulate the
	 * {@link Service#runningProperty()}.
	 */
	private BooleanProperty getRunningPropertyReadable() {
		return (BooleanProperty) runningProperty();
	}
	
	protected void callActionAndSynthesizeServiceRun() {
		try {
			unbindServiceExceptionFromTaskException();
			getStateObjectPropertyReadable().setValue(State.SCHEDULED);
			
			// We call the User Action. If an exception occurs we save it, therefore we can use it in the Test
			// (createSynthesizedTask) to throw it during the Service invocation.
			Action action = actionSupplier.get();
			setWritableExceptionProperty(action);
			bindServiceExceptionToTaskException();

			getRunningPropertyReadable().set(true);
			action.action();
			getRunningPropertyReadable().set(false);

			getStateObjectPropertyReadable().setValue(State.SUCCEEDED);
		} catch (Exception e) {
			setException(e);
			LOG.error("Exception in Command Execution", writableExceptionProperty.getValue());
			getStateObjectPropertyReadable().setValue(State.FAILED);
		}
	}
	
	@Override
	protected Task<Void> createTask() {
		if (inBackground) {
			return actionSupplier.get();
		}
		return createSynthesizedTask();
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
	
	private Task<Void> createSynthesizedTask() {
		return new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				return null;
			}
		};
	}
	
	/**
	 * Binds the {@link Service#exceptionProperty()} to the {@link Action#exceptionProperty()} like in Service.start().
	 */
	private void bindServiceExceptionToTaskException() {
		checkExceptionProperty(super.exceptionProperty()).bind(writableExceptionProperty);
	}
	
	/**
	 * Unbinds the {@link Service#exceptionProperty()} from {@link Action#exceptionProperty()}.
	 */
	private void unbindServiceExceptionFromTaskException() {
		checkExceptionProperty(super.exceptionProperty()).unbind();
	}
	
	/**
	 * Creates a writable exception property based on the exception property of the given action.
	 */
	private void setWritableExceptionProperty(Action action) {
		writableExceptionProperty = checkExceptionProperty(action.exceptionProperty());
	}
	
	/**
	 * Set the given exception to exception property of the task.
	 * 
	 * @param throwable
	 *            The exception
	 */
	private void setException(Throwable throwable) {
		writableExceptionProperty.setValue(throwable);
	}
	
	@SuppressWarnings("unchecked")
	protected static Property<Throwable> checkExceptionProperty(ReadOnlyObjectProperty<Throwable> exceptionProperty) {
		if (exceptionProperty instanceof Property) {
			return (Property<Throwable>) exceptionProperty;
		} else {
			// in JavaFX the implementation class of most ReadOnlyProperty<Throwable> instances in Service/Task are
			// actually of type ObjectProperty<Throwable>
			// and therefor can be casted so that we are able to bind them. This is a dependency to an implementation
			// detail of JavaFX but at the moment there is no other
			// way of achieving the desired behavior.
			// If for some reason a future update of the JavaFX implementation changes the actual implementation class
			// we can't use binding anymore and need to find another way to implement our use cases.
			throw new RuntimeException(
					"Cannot use DelegateCommand in asynchronous mode because of an incompatible JDK version. Please report this to the mvvmFX developers at https://github.com/sialcasa/mvvmFX.");
		}
	}

	public Supplier<Action> getActionSupplier() {
		return actionSupplier;
	}
}
