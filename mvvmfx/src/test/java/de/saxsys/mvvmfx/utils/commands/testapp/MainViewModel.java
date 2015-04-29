package de.saxsys.mvvmfx.utils.commands.testapp;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.CompositeCommand;

/**
 * @author manuel.mauky
 */
public class MainViewModel implements ViewModel {
	
	private SubViewModel childOne;
	private SubViewModel childTwo;
	private SubViewModel childThree;
	
	
	private Command rollAllDicesCommand;

	public void init(){
		rollAllDicesCommand = new CompositeCommand(childOne.getRollDiceCommand(), childTwo.getRollDiceCommand(), childThree.getRollDiceCommand());
	}
	
	public Command getRollAllDicesCommand() {
		return rollAllDicesCommand;
	}
	
	public void setChildOne(SubViewModel childOne) {
		this.childOne = childOne;
	}

	public void setChildTwo(SubViewModel childTwo) {
		this.childTwo = childTwo;
	}

	public void setChildThree(SubViewModel childThree) {
		this.childThree = childThree;
	}
}
