package de.saxsys.mvvmfx.utils.commands;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker.State;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.cedarsoft.test.utils.CatchAllExceptionsRule;

import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;



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

		DelegateCommand delegateCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() {
				called.set(true);
				throw new RuntimeException("Some reason");
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
		Platform.runLater(() ->
				assertThat(command.getProgress()).isEqualTo(0.0));
		
		stepTwo.get(1, TimeUnit.SECONDS);
		Platform.runLater(() ->
				assertThat(command.getProgress()).isEqualTo(0.3, offset(0.1)));
		
		stepThree.get(1, TimeUnit.SECONDS);
		Platform.runLater(() ->
				assertThat(command.getProgress()).isEqualTo(0.6, offset(0.1)));
		
		stepFour.get(1, TimeUnit.SECONDS);
		Platform.runLater(() ->
				assertThat(command.getProgress()).isEqualTo(1, offset(0.1)));
		
		// sleep to prevent the Junit thread from exiting
		// before eventual assertion errors from the JavaFX Thread are detected
		sleep(500);
	}
	
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
