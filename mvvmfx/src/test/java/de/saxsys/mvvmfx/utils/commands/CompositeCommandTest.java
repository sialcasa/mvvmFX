package de.saxsys.mvvmfx.utils.commands;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.testingutils.GCVerifier;

public class CompositeCommandTest {
	
	private BooleanProperty condition1;
	private BooleanProperty called1;
	private DelegateCommand delegateCommand1;
	private BooleanProperty condition2;
	private BooleanProperty called2;
	private DelegateCommand delegateCommand2;
	
	@Before
	public void init() {
		condition1 = new SimpleBooleanProperty(true);
		called1 = new SimpleBooleanProperty();
		delegateCommand1 = new DelegateCommand(() -> called1.set(true), condition1);
		
		condition2 = new SimpleBooleanProperty(true);
		called2 = new SimpleBooleanProperty();
		delegateCommand2 = new DelegateCommand(() -> called2.set(true), condition2);
	}
	
	@Test
	public void executable() throws Exception {
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1, delegateCommand2);
		
		GCVerifier.forceGC();
		
		assertTrue(compositeCommand.isExecutable());
		assertFalse(compositeCommand.isNotExecutable());
		
		condition1.set(false);
		
		assertFalse(compositeCommand.isExecutable());
		assertTrue(compositeCommand.isNotExecutable());
		
		condition2.set(false);
		
		assertFalse(compositeCommand.isExecutable());
		assertTrue(compositeCommand.isNotExecutable());
		
		condition1.set(true);
		
		assertFalse(compositeCommand.isExecutable());
		assertTrue(compositeCommand.isNotExecutable());
		
		condition2.set(true);
		
		assertTrue(compositeCommand.isExecutable());
		assertFalse(compositeCommand.isNotExecutable());
	}
	
	@Test
	public void executable2() throws Exception {
		CompositeCommand compositeCommand = new CompositeCommand();
		GCVerifier.forceGC();
		
		
		assertThat(compositeCommand.isExecutable()).isTrue();
		assertThat(compositeCommand.isNotExecutable()).isFalse();
		
		compositeCommand.register(delegateCommand1);
		GCVerifier.forceGC();
		
		assertThat(compositeCommand.isExecutable()).isTrue();
		assertThat(compositeCommand.isNotExecutable()).isFalse();
		
		condition1.setValue(false);
		assertThat(compositeCommand.isExecutable()).isFalse();
		assertThat(compositeCommand.isNotExecutable()).isTrue();
		
		condition1.setValue(true);
		assertThat(compositeCommand.isExecutable()).isTrue();
		assertThat(compositeCommand.isNotExecutable()).isFalse();
		
		condition2.setValue(false);
		assertThat(compositeCommand.isExecutable()).isTrue();
		assertThat(compositeCommand.isNotExecutable()).isFalse();
		
		compositeCommand.register(delegateCommand2);
		GCVerifier.forceGC();
		assertThat(compositeCommand.isExecutable()).isFalse();
		assertThat(compositeCommand.isNotExecutable()).isTrue();
		
		compositeCommand.unregister(delegateCommand2);
		GCVerifier.forceGC();
		assertThat(compositeCommand.isExecutable()).isTrue();
		assertThat(compositeCommand.isNotExecutable()).isFalse();
	}
	
	@Test
	public void register() throws Exception {
		
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1);
		
		assertTrue(compositeCommand.isExecutable());
		assertFalse(compositeCommand.isNotExecutable());
		// prepare delegateCommand2
		condition2.set(false);
		
		compositeCommand.register(delegateCommand2);
		assertFalse(compositeCommand.isExecutable());
		assertTrue(compositeCommand.isNotExecutable());
		
		compositeCommand.unregister(delegateCommand2);
		assertTrue(compositeCommand.isExecutable());
		assertFalse(compositeCommand.isNotExecutable());
	}
	
	@Test
	public void running() throws Exception {
		BooleanProperty run = new SimpleBooleanProperty();
		BooleanProperty finished = new SimpleBooleanProperty();
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1, delegateCommand2);
		
		// We have to check the running Property with this mechanism, because it is processed synchronously and we can't
		// hook between the state changes.
		compositeCommand.runningProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
			if (!oldValue && newValue) {
				run.set(true);
				assertTrue(compositeCommand.runningProperty().get());
			}
			if (oldValue && !newValue) {
				finished.set(true);
				assertFalse(compositeCommand.runningProperty().get());
			}
		});
		
		compositeCommand.execute();
		
		assertTrue(run.get());
		assertTrue(finished.get());
	}
	
	@Test
	public void allCommandsAreUnregistered() throws Exception {
		
		// UncaughtExceptionHandler is defined to be able to detect exception from listeners.
		Thread.currentThread().setUncaughtExceptionHandler(
				(thread, exception) -> fail("Exception was thrown", exception));
		
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1, delegateCommand2);
		
		compositeCommand.unregister(delegateCommand1);
		compositeCommand.unregister(delegateCommand2);
	}
	
	@Test
	public void longRunningAsyncComposite() throws Exception {
		
		BooleanProperty condition = new SimpleBooleanProperty(true);
		
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		DelegateCommand delegateCommand1 = new DelegateCommand(() -> sleep(500), condition, true);
		
		DelegateCommand delegateCommand2 = new DelegateCommand(() -> {
			sleep(1000);
			future.complete(null);
		}, condition, true);
		
		DelegateCommand delegateCommand3 = new DelegateCommand(() -> {
		}, condition, false);
		
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1, delegateCommand2, delegateCommand3);
		
		GCVerifier.forceGC();
		
		assertFalse(compositeCommand.runningProperty().get());
		assertFalse(delegateCommand1.runningProperty().get());
		assertFalse(delegateCommand2.runningProperty().get());
		assertFalse(delegateCommand3.runningProperty().get());
		assertTrue(compositeCommand.notRunningProperty().get());
		assertTrue(delegateCommand1.notRunningProperty().get());
		assertTrue(delegateCommand2.notRunningProperty().get());
		assertTrue(delegateCommand3.notRunningProperty().get());
		
		compositeCommand.execute();
		
		assertTrue(compositeCommand.runningProperty().get());
		assertTrue(delegateCommand1.runningProperty().get());
		assertTrue(delegateCommand2.runningProperty().get());
		assertFalse(delegateCommand3.runningProperty().get());
		assertFalse(compositeCommand.notRunningProperty().get());
		assertFalse(delegateCommand1.notRunningProperty().get());
		assertFalse(delegateCommand2.notRunningProperty().get());
		assertTrue(delegateCommand3.notRunningProperty().get());
		
		future.get(3, TimeUnit.SECONDS);
		
		assertFalse(compositeCommand.runningProperty().get());
		assertFalse(delegateCommand1.runningProperty().get());
		assertFalse(delegateCommand2.runningProperty().get());
		assertFalse(delegateCommand3.runningProperty().get());
		assertTrue(compositeCommand.notRunningProperty().get());
		assertTrue(delegateCommand1.notRunningProperty().get());
		assertTrue(delegateCommand2.notRunningProperty().get());
		assertTrue(delegateCommand3.notRunningProperty().get());
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
