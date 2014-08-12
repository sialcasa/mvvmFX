package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;


public class TestFxmlViewWithoutViewModelField implements FxmlView<TestViewModel> {

	public boolean wasInitialized = false;

	public void initialize() {
		wasInitialized = true;
	}
}
