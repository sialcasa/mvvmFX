package de.saxsys.mvvmfx.internal.viewloader.builderfactory;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

public class BuilderFactoryTestView implements FxmlView<BuilderFactoryTestViewModel> {

	@FXML
	public CustomTextField textField;

	@InjectViewModel
	private BuilderFactoryTestViewModel viewModel;

	public void initialize() {
	}
}
