package de.saxsys.mvvmfx.utils.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

import org.junit.Before;
import org.junit.Test;

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
	public void executeable() throws Exception {
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1, delegateCommand2);
		
		assertTrue(compositeCommand.isExecuteable());
		assertTrue(compositeCommand.executeableProperty().get());
		
		condition1.set(false);
		
		assertFalse(compositeCommand.isExecuteable());
		assertFalse(compositeCommand.executeableProperty().get());
		
		condition2.set(false);
		
		assertFalse(compositeCommand.isExecuteable());
		assertFalse(compositeCommand.executeableProperty().get());
		
		condition1.set(true);
		
		assertFalse(compositeCommand.isExecuteable());
		assertFalse(compositeCommand.executeableProperty().get());
		
		condition2.set(true);
		
		assertTrue(compositeCommand.isExecuteable());
		assertTrue(compositeCommand.executeableProperty().get());
	}
	
	@Test
	public void register() throws Exception {
		
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1);
		
		assertTrue(compositeCommand.isExecuteable());
		assertTrue(compositeCommand.executeableProperty().get());
		// prepare delegateCommand2
		condition2.set(false);
		compositeCommand.register(delegateCommand2);
		assertFalse(compositeCommand.isExecuteable());
		assertFalse(compositeCommand.executeableProperty().get());
		compositeCommand.unregister(delegateCommand2);
		assertTrue(compositeCommand.isExecuteable());
		assertTrue(compositeCommand.executeableProperty().get());
	}
	
	@Test
	public void running() throws Exception {
		BooleanProperty run = new SimpleBooleanProperty();
		BooleanProperty finished = new SimpleBooleanProperty();
		CompositeCommand compositeCommand = new CompositeCommand(delegateCommand1, delegateCommand2);
		
		compositeCommand.runningProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
			if (!oldValue && newValue)
				run.set(true);
			if (oldValue && !newValue)
				finished.set(true);
		});
		
		compositeCommand.execute();
		
		assertTrue(run.get());
		assertTrue(finished.get());
	}
}
