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

import com.cedarsoft.test.utils.CatchAllExceptionsRule;
import de.saxsys.mvvmfx.testingutils.FxTestingUtils;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



@RunWith(JfxRunner.class)
public class DelegateCommandTest {

	// Rule to get exceptions from the JavaFX Thread into the JUnit thread
	@Rule
	public CatchAllExceptionsRule catchAllExceptionsRule = new CatchAllExceptionsRule();

	@Test
	public void executable() {
		BooleanProperty condition = new SimpleBooleanProperty(true);

		DelegateCommand delegateCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() {
			}
		}, condition);

		assertTrue(delegateCommand.isExecutable());
		assertFalse(delegateCommand.isNotExecutable());

		condition.set(false);

		assertFalse(delegateCommand.isExecutable());
		assertTrue(delegateCommand.isNotExecutable());

		condition.set(true);

		assertTrue(delegateCommand.isExecutable());
		assertFalse(delegateCommand.isNotExecutable());
	}

	@Test
	public void executeSynchronousSucceeded() throws Exception {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		BooleanProperty called = new SimpleBooleanProperty();
		BooleanProperty succeeded = new SimpleBooleanProperty();
		BooleanProperty failed = new SimpleBooleanProperty();

		DelegateCommand delegateCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() {
				called.set(true);
			}
		}, condition);

		delegateCommand.setOnSucceeded(workerStateEvent -> {
			succeeded.set(true);
		});

		delegateCommand.setOnFailed(workerStateEvent -> {
			failed.set(true);
		});

		// given
		assertThat(called.get()).isFalse();
		assertThat(succeeded.get()).isFalse();
		assertThat(failed.get()).isFalse();

		// when
		delegateCommand.execute();

		// then
		assertThat(called.get()).isTrue();
		assertThat(succeeded.get()).isTrue();
		assertThat(failed.get()).isFalse();
	}

	@Test
	public void executeSynchronousFailed() throws Exception {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		BooleanProperty called = new SimpleBooleanProperty();
		BooleanProperty succeeded = new SimpleBooleanProperty();
		BooleanProperty failed = new SimpleBooleanProperty();
		final String exceptionReason = "Some reason";

		DelegateCommand delegateCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() {
				called.set(true);
				throw new RuntimeException(exceptionReason);
			}
		}, condition);

		delegateCommand.setOnSucceeded(workerStateEvent -> {
			succeeded.set(true);
		});

		delegateCommand.setOnFailed(workerStateEvent -> {
			failed.set(true);
		});

		// given
		assertThat(called.get()).isFalse();
		assertThat(succeeded.get()).isFalse();
		assertThat(failed.get()).isFalse();

		// when
		delegateCommand.execute();

		// then
		assertThat(called.get()).isTrue();
		assertThat(succeeded.get()).isFalse();
		assertThat(failed.get()).isTrue();

		assertThat(delegateCommand.exceptionProperty().get())
				.isNotNull()
				.isInstanceOf(RuntimeException.class)
				.hasMessage(exceptionReason);
	}

	@Test(expected = RuntimeException.class)
	public void commandNotExecutable() {
		BooleanProperty condition = new SimpleBooleanProperty(false);

		DelegateCommand delegateCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() {
			}
		}, condition);

		delegateCommand.execute();
	}

	@Test
	public void longRunningAsync() throws Exception {

		BooleanProperty condition = new SimpleBooleanProperty(true);

		CompletableFuture<Void> commandStarted = new CompletableFuture<>();
		CompletableFuture<Void> commandCompleted = new CompletableFuture<>();

		DelegateCommand delegateCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				Thread.sleep(1000);
			}
		}, condition, true);

		assertFalse(delegateCommand.runningProperty().get());
		assertTrue(delegateCommand.notRunningProperty().get());

		delegateCommand.runningProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
			if (newValue) {
				assertTrue(delegateCommand.runningProperty().get());
				assertFalse(delegateCommand.notRunningProperty().get());
				assertFalse(delegateCommand.executableProperty().get());
				assertTrue(delegateCommand.notExecutableProperty().get());
				commandStarted.complete(null);
			}
			if (!newValue && oldValue) {
				assertFalse(delegateCommand.runningProperty().get());
				assertTrue(delegateCommand.notRunningProperty().get());
				assertTrue(delegateCommand.executableProperty().get());
				assertFalse(delegateCommand.notExecutableProperty().get());
				commandCompleted.complete(null);
			}

		});

		delegateCommand.execute();
		commandStarted.get(3, TimeUnit.SECONDS);
		commandCompleted.get(4, TimeUnit.SECONDS);
	}


	@Test
	public void progressProperty() throws Exception {

		CompletableFuture<Void> stepOne = new CompletableFuture<>();
		CompletableFuture<Void> stepTwo = new CompletableFuture<>();
		CompletableFuture<Void> stepThree = new CompletableFuture<>();
		CompletableFuture<Void> stepFour = new CompletableFuture<>();

		DelegateCommand command = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				updateProgress(0, 3);
				stepOne.complete(null);
				sleep(500);
				updateProgress(1, 3);
				stepTwo.complete(null);
				sleep(500);
				updateProgress(2, 3);
				stepThree.complete(null);
				sleep(500);
				updateProgress(3, 3);
				stepFour.complete(null);
			}
		}, true);

		command.execute();

		stepOne.get(1, TimeUnit.SECONDS);
		Platform.runLater(() -> assertThat(command.getProgress()).isEqualTo(0.0));

		stepTwo.get(1, TimeUnit.SECONDS);
		Platform.runLater(() -> assertThat(command.getProgress()).isEqualTo(0.3, offset(0.1)));

		stepThree.get(1, TimeUnit.SECONDS);
		Platform.runLater(() -> assertThat(command.getProgress()).isEqualTo(0.6, offset(0.1)));

		stepFour.get(1, TimeUnit.SECONDS);
		Platform.runLater(() -> assertThat(command.getProgress()).isEqualTo(1, offset(0.1)));

		// sleep to prevent the Junit thread from exiting
		// before eventual assertion errors from the JavaFX Thread are detected
		sleep(500);
	}

	/**
	 * This test verifies the behaviour when the delegate command is restarted.
	 * <p>
	 * It defines a test that is both executed with a pure JavaFX {@link Service} and the mvvmFX {@link DelegateCommand}
	 * . This way we can verify that both implementations are behaving equally.
	 *
	 * @throws Exception
	 */
	@Test
	public void testRestartAsync() throws Exception {
		// these lists are used to keep track which actions are called, succeeded etc.
		// each action has a number that will be added to these lists
		List<Integer> called = new ArrayList<>();
		List<Integer> succeeded = new ArrayList<>();
		List<Integer> cancelled = new ArrayList<>();
		List<Integer> interrupted = new ArrayList<>();


		// A special action that will do nothing but wait for 500 ms when called.
		class MyAction extends Action {

			private final int number;

			MyAction(int number) {
				this.number = number;
			}

			@Override
			protected void action() throws Exception {
				called.add(this.number);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// keep track if this action is interrupted
					interrupted.add(this.number);
				}
			}

			@Override
			protected void succeeded() {
				succeeded.add(this.number);
			}

			@Override
			protected void cancelled() {
				cancelled.add(this.number);
			}
		}

		// A pure JavaFX service that used our test action class as task
		Service<Void> jfxService = new Service<Void>() {
			int counter = 0;

			@Override
			protected Task<Void> createTask() {
				// as "Action" extends from "Task<Void>" we can use it here
				MyAction myAction = new MyAction(counter);
				counter++;
				return myAction;
			}
		};


		// A mvvmFX command that uses the action class as task
		DelegateCommand command = new DelegateCommand(new Supplier<Action>() {
			int counter = 0;

			@Override
			public Action get() {
				MyAction myAction = new MyAction(counter);
				counter++;
				return myAction;
			}
		}, true);


		// The actual testing steps are encapsulated in a function (BiConsumer) that
		// takes two runnables as argument. The first runnable starts the service for the first time.
		// The second runnable restarts the service.
		// This way we can reuse the same testing steps on both variants.
		BiConsumer<Runnable, Runnable> test = (startService, restartService) -> {
			called.clear();
			succeeded.clear();
			cancelled.clear();
			interrupted.clear();

			startService.run();
			sleep(100);
			FxTestingUtils.waitForUiThread();

			assertThat(called).containsExactly(0); // the first action is called
			assertThat(succeeded).isEmpty();
			assertThat(cancelled).isEmpty();
			assertThat(interrupted).isEmpty();


			// restart and all other interactions with the service have to be done
			// on the UI-thread. Therefore we use Platform.runLater
			Platform.runLater(restartService);
			sleep(300);
			// We need to wait for the UI-Thread to execute all enqueued runnables
			FxTestingUtils.waitForUiThread();

			assertThat(called).containsExactly(0, 1); // now the second action is called too
			assertThat(succeeded).isEmpty();
			assertThat(cancelled).containsExactly(0); // the first one is cancelled ...
			assertThat(interrupted).containsExactly(0); // and interrupted

			// the normal execution of the action takes 500 ms so we need to wait a little longer
			sleep(1000);
			FxTestingUtils.waitForUiThread();

			assertThat(called).containsExactly(0, 1);
			assertThat(succeeded).containsExactly(1); // now the second action was finished successfully
			assertThat(cancelled).containsExactly(0);
			assertThat(interrupted).containsExactly(0);
		};

		// run the test on both the pure JavaFX service and the delegate command

		// JavaFX Service uses "start" for initial startup and "restart" for the second start
		test.accept(jfxService::start, jfxService::restart);

		// DelegateCommand uses "execute" both times
		test.accept(command::execute, command::execute);
	}

	@Test
	public void testCheckExceptionProperty() {
		DelegateCommand delegateCommand = new DelegateCommand(() -> {
			return null;
		});
		DelegateCommand.checkExceptionProperty(delegateCommand.exceptionProperty());
	}

	@Test
	public void testSynchronousActionException() {
		final String exceptionReason = "Some reason";

		Action action = new Action() {
			@Override
			protected void action() throws Exception {
				throw new RuntimeException(exceptionReason);
			}
		};

		DelegateCommand delegateCommand = new DelegateCommand(() -> action);
		delegateCommand.execute();
		assertThat(action.getException())
				.isNotNull()
				.isInstanceOf(RuntimeException.class)
				.hasMessage(exceptionReason);
	}



	@Test
	public void testExceptionInActionSupplier() {

		Supplier<Action> actionSupplier = () -> {
			throw new RuntimeException("test");
		};

		DelegateCommand command = new DelegateCommand(actionSupplier);

		command.execute();

		assertThat(command.exceptionProperty().get()).hasMessage("test");

	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
