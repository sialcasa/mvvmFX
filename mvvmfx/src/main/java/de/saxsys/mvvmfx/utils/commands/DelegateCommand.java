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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.lestard.doc.Beta;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.value.ObservableBooleanValue;
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
	
	private final Supplier<Action> actionSupplier;
	private Action action;
	private boolean inBackground = false;
	protected final ReadOnlyBooleanWrapper executable = new ReadOnlyBooleanWrapper(true);
	protected ReadOnlyBooleanWrapper notExecutable;
	protected ReadOnlyBooleanWrapper notRunning;

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
		this.action = actionSupplier.get();
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
				super.reset();
				bindServiceExceptionToTaskException();
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
	
	private void callActionAndSynthesizeServiceRun() {
		try {
			getStateObjectPropertyReadable().setValue(State.SCHEDULED);
			
			// We call the User Action. If an exception occurs we save it, therefore we can use it in the Test
			// (createSynthesizedTask) to throw it during the Service invocation.
			action.action();
			
			getStateObjectPropertyReadable().setValue(State.SUCCEEDED);
		} catch (Exception e) {
			LOG.error("Exception in Command Execution", e);
			setException(e);
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
	 * Binds the {@link Service#exceptionProperty()} to the {@link Action#exceptionProperty()}
	 * like in Service.start().
	 */
	private void bindServiceExceptionToTaskException() {
		((ObjectProperty<Throwable>) super.exceptionProperty()).bind(action.exceptionProperty());
	}

	/**
	 * Returns the exception from the superclass as writable property.<br>
	 *     {@link Service#exceptionProperty()} is a readonly property, though not really.
	 *     Internal its a ObjectProperty&lt;Throwable&gt;.
	 * @return
	 * The casted writable exception property.
	 */
	private void setException(Throwable throwable) {
		((SimpleObjectProperty<Throwable>) action.exceptionProperty()).set(throwable);
	}
}
