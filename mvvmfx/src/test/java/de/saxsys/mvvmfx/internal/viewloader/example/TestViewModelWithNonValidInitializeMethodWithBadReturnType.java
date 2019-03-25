package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.ViewModel;

public class TestViewModelWithNonValidInitializeMethodWithBadReturnType implements ViewModel {
	public static boolean init = false;

	/**
	 * Not a valid initialize method
	 */
	public String initialize() {
		init = true;

		return "something";
	}

	public void lala() {

	}

}
