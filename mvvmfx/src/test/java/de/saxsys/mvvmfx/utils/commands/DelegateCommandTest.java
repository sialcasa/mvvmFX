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
	public void executeable() {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		
		DelegateCommand delegateCommand = new DelegateCommand(() -> {
		}, condition);
		
		assertTrue(delegateCommand.isExecutable());
		assertTrue(delegateCommand.executableProperty().get());
		
		condition.set(false);
		
		assertFalse(delegateCommand.isExecutable());
		assertFalse(delegateCommand.executableProperty().get());
		
		condition.set(true);
		
		assertTrue(delegateCommand.isExecutable());
		assertTrue(delegateCommand.executableProperty().get());
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
			if (!oldValue && newValue)
				run.set(true);
			if (oldValue && !newValue)
				finished.set(true);
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
		delegateCommand.execute();
		assertTrue(delegateCommand.runningProperty().get());
		assertFalse(delegateCommand.executableProperty().get());
		future.get(3, TimeUnit.SECONDS);
		assertFalse(delegateCommand.runningProperty().get());
		assertTrue(delegateCommand.executableProperty().get());
	}
	
}
