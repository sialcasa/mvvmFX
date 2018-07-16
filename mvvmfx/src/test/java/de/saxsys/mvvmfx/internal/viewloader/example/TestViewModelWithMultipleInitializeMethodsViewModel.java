package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.ViewModel;

public class TestViewModelWithMultipleInitializeMethodsViewModel implements ViewModel {

	public static boolean wrongInit = false;
	public static boolean correctInit = false;

	/**
	 * Not a valid initialize method
	 */
	public void initialize(String someParam) {
		wrongInit = true;
	}

	/**
	 * A valid initialize method
	 */
	public void initialize() {
		correctInit = true;
	}

}
