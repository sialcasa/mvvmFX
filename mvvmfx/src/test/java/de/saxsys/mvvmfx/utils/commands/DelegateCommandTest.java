package de.saxsys.mvvmfx.utils.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

import org.junit.Test;


public class DelegateCommandTest {
	
	@Test
	public void executeable() throws Exception {
		BooleanProperty condition = new SimpleBooleanProperty(true);
		
		DelegateCommand delegateCommand = new DelegateCommand(() -> {
		}, condition);
		
		assertTrue(delegateCommand.isExecuteable());
		assertTrue(delegateCommand.executeableProperty().get());
		
		condition.set(false);
		
		assertFalse(delegateCommand.isExecuteable());
		assertFalse(delegateCommand.executeableProperty().get());
		
		condition.set(true);
		
		assertTrue(delegateCommand.isExecuteable());
		assertTrue(delegateCommand.executeableProperty().get());
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
	
}
