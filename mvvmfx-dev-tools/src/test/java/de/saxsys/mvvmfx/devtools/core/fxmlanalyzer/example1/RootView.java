package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class RootView implements FxmlView<RootViewModel> {

	@InjectViewModel
	private RootViewModel viewModel;

	public void initialize() {

	}
}
