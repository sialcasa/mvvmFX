package de.saxsys.mvvmfx.utils.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;


@RunWith(JfxRunner.class)
public class DelegateCommandTest {
	
	@Test
	public void executable() {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		
		DelegateCommand delegateCommand = new DelegateCommand(new Action() {
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
	public void firePositive() {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		BooleanProperty called = new SimpleBooleanProperty();
		
		DelegateCommand delegateCommand = new DelegateCommand(new Action() {
			@Override
			protected void action() {
				called.set(true);
			}
		}, condition);
		
		assertFalse(called.get());
		delegateCommand.execute();
		assertTrue(called.get());
	}
	
	@Test(expected = RuntimeException.class)
	public void fireNegative() {
		BooleanProperty condition = new SimpleBooleanProperty(false);
		
		DelegateCommand delegateCommand = new DelegateCommand(new Action() {
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
		
		DelegateCommand delegateCommand = new DelegateCommand(new Action() {
			@Override
			protected void action() throws Exception {
				Thread.sleep(1000);
			}
		}, condition, true);
		
		assertFalse(delegateCommand.runningProperty().get());
		assertTrue(delegateCommand.notRunningProperty().get());
		
		delegateCommand.runningProperty().addListener(new ChangeListener<Boolean>() {
			
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
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
				
			}
		});
		
		delegateCommand.execute();
		commandStarted.get(3, TimeUnit.SECONDS);
		commandCompleted.get(4, TimeUnit.SECONDS);
	}
	
}
