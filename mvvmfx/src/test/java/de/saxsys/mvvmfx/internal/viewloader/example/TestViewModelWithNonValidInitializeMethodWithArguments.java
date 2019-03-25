package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.ViewModel;

public class TestViewModelWithNonValidInitializeMethodWithArguments implements ViewModel {

	public static boolean init = false;

	/**
	 * Not a valid initialize method
	 */
	public void initialize(String someArgument) {
		init = true;
	}

}
