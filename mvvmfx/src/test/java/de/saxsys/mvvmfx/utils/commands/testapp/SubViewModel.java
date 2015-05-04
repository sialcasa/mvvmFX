package de.saxsys.mvvmfx.utils.commands.testapp;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.IntStream;

/**
 * @author manuel.mauky
 */
public class SubViewModel implements ViewModel {
	
	private static final int NUMBERS = 10;
	
	private ObservableList<Integer> numbers = FXCollections.observableArrayList();
	
	private Command rollDiceCommand;
	
	private BooleanProperty active = new SimpleBooleanProperty(true);
	
	public SubViewModel(){
		
		Service service = new Service();
		
		rollDiceCommand = new DelegateCommand(() -> new Action() {
			@Override
			protected void action() throws Exception {
				updateProgress(0, NUMBERS);
				Platform.runLater(numbers::clear);
				for (int i = 0; i < NUMBERS; i++) {
					final int newNumber = service.longRunningService();

					updateProgress(i + 1, NUMBERS);
					Platform.runLater(() -> numbers.add(newNumber));
				}
			}
		}, active, true);
		
		
	}
	
	public Command getRollDiceCommand(){
		return rollDiceCommand;
	}
	
	public ObservableList<Integer> numbersProperty(){
		return numbers;
	}
	 
	public BooleanProperty activeProperty(){
		return active;
	}
}
