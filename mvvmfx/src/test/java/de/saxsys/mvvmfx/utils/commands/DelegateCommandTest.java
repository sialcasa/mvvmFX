package de.saxsys.mvvmfx.utils.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

import org.junit.Test;


public class DelegateCommandTest {
	
	@Test
	public void executable() {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		
		DelegateCommand delegateCommand = new DelegateCommand(() -> {
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
	public void firePositive() {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		BooleanProperty called = new SimpleBooleanProperty();
		
		DelegateCommand delegateCommand = new DelegateCommand(() -> called.set(true), condition);
		
		assertFalse(called.get());
		delegateCommand.execute();
		assertTrue(called.get());
	}
	
	@Test(expected = RuntimeException.class)
	public void fireNegative() {
		BooleanProperty condition = new SimpleBooleanProperty(false);
		
		DelegateCommand delegateCommand = new DelegateCommand(() -> {
		}, condition);
		
		delegateCommand.execute();
	}
	
	
	@Test
	public void running() throws Exception {
		BooleanProperty run = new SimpleBooleanProperty();
		BooleanProperty finished = new SimpleBooleanProperty();
		
		BooleanProperty condition = new SimpleBooleanProperty(true);
		
		DelegateCommand delegateCommand = new DelegateCommand(() -> {
		}, condition);
		
		delegateCommand.runningProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
			if (!oldValue && newValue) {
				run.set(true);
				assertFalse(delegateCommand.notRunningProperty().get());
			}
			if (oldValue && !newValue) {
				finished.set(true);
				assertTrue(delegateCommand.notRunningProperty().get());
			}
		});
		
		delegateCommand.execute();
		
		assertTrue(run.get());
		assertTrue(finished.get());
	}
	
	
	@Test
	public void longRunningAsync() throws Exception {
		
		BooleanProperty condition = new SimpleBooleanProperty(true);
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		DelegateCommand delegateCommand = new DelegateCommand(() -> {
			try {
				Thread.sleep(1000);
				future.complete(null);
			} catch (Exception e) {
			}
		}, condition, true);
		
		assertFalse(delegateCommand.runningProperty().get());
		assertTrue(delegateCommand.notRunningProperty().get());
		delegateCommand.execute();
		
		assertTrue(delegateCommand.runningProperty().get());
		assertFalse(delegateCommand.notRunningProperty().get());
		assertFalse(delegateCommand.executableProperty().get());
		assertTrue(delegateCommand.notExecutableProperty().get());
		
		future.get(3, TimeUnit.SECONDS);
		assertFalse(delegateCommand.runningProperty().get());
		assertTrue(delegateCommand.notRunningProperty().get());
		assertTrue(delegateCommand.executableProperty().get());
		assertFalse(delegateCommand.notExecutableProperty().get());
	}
	
}
