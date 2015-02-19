package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;


public class TestFxmlViewWithoutViewModelField implements FxmlView<TestViewModel> {

	public static int instanceCounter = 0;
	
	public boolean wasInitialized = false;

	public TestFxmlViewWithoutViewModelField() {
		instanceCounter++;
	}

	public void initialize() {
		wasInitialized = true;
	}
}
