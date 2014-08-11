package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.FxmlView;


public class TestFxmlViewWithoutViewModelField implements FxmlView<TestViewModel> {

	public boolean wasInitialized = false;

	public void initialize() {
		wasInitialized = true;
	}
}
