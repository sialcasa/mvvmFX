package de.saxsys.jfx.mvvm.viewloader.example;

import de.saxsys.jfx.mvvm.api.InjectViewModel;
import de.saxsys.jfx.mvvm.api.JavaView;
import javafx.scene.layout.VBox;

public class TestJavaViewMultipleViewModels extends VBox implements JavaView<TestViewModel> {
	
	@InjectViewModel
	public TestViewModel viewModel1;
	
	@InjectViewModel
	public TestViewModel viewModel2;
	
}
