package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class TestViewA implements FxmlView<TestViewModelA> {
	
	@InjectViewModel
	public TestViewModelA viewModel;

	@FXML
	public VBox testViewB;
	
	@FXML
	public TestViewB testViewBController;

	public boolean initializeWasCalled = false;

	public void initialize(){
		initializeWasCalled = true;
	}

}
