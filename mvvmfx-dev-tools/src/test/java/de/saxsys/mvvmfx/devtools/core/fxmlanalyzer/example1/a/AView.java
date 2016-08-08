package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.a;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class AView implements FxmlView<AViewModel> {

	@InjectViewModel
	private AViewModel viewModel;

	public void initialize() {

	}
}
