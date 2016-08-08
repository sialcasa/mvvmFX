package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class BView implements FxmlView<BViewModel> {

	@InjectViewModel
	private BViewModel viewModel;

	public void initialize() {

	}
}
