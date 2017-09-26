package de.saxsys.mvvmfx.utils.commands;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * There are use cases where the commands have to be used without a UI thread, for example when running unit tests.
 * 
 * To verify this behaviour we use a dedicated test class where we don't use a special JUnit runner for UI threading.
 */
public class CommandsWithoutUiThreadTest {
	
	public class MyViewModel {
		
		public IntegerProperty a = new SimpleIntegerProperty();
		public IntegerProperty b = new SimpleIntegerProperty();
		public IntegerProperty result = new SimpleIntegerProperty();
		
		private final DelegateCommand calcCommand;
		
		public MyViewModel() {
			
			BooleanProperty executable = new SimpleBooleanProperty();
			executable.bind(a.greaterThan(0).and(b.greaterThan(0)));
			
			calcCommand = new DelegateCommand(() -> {
				return new Action() {
					@Override
					protected void action() throws Exception {
						result.set(a.get() + b.get());
					}
				};
			}, executable, false);
		}
		
		public Command calcCommand() {
			return calcCommand;
		}
	}
	
	
	@Test
	public void test() {
		
		MyViewModel viewModel = new MyViewModel();
		assertThat(viewModel.calcCommand.isExecutable()).isFalse();
		assertThat(viewModel.result.get()).isEqualTo(0);
		
		
		viewModel.a.setValue(2);
		assertThat(viewModel.calcCommand.isExecutable()).isFalse();
		assertThat(viewModel.result.get()).isEqualTo(0);
		
		viewModel.b.setValue(3);
		assertThat(viewModel.calcCommand.isExecutable()).isTrue();
		assertThat(viewModel.result.get()).isEqualTo(0);
		
		viewModel.calcCommand.execute();
		
		assertThat(viewModel.result.get()).isEqualTo(5);
		
	}
	
	
	
}
