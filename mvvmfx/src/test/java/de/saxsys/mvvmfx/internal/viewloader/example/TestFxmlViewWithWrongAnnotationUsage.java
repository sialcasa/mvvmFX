package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class TestFxmlViewWithWrongAnnotationUsage implements FxmlView<TestViewModel> {
	
	// No ViewModel type
	@InjectViewModel
	private Object something;
	
}
