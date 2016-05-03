package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectContext;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class ScopedFxmlViewE implements FxmlView<ScopedViewModelE> {


	@FXML
	public ScopedFxmlViewF subviewFController;

	@FXML
	public ScopedFxmlViewG subviewGController;


	@InjectViewModel
	public ScopedViewModelE viewModel;


	public ScopedFxmlViewE() {
		System.out.println("new " + this.getClass().getSimpleName() + "()");
	}

	public void initialize() {
		System.out.println(this.getClass().getSimpleName() + ".initialize()");
	}
}
