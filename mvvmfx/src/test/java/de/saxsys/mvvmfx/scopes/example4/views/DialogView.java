package de.saxsys.mvvmfx.scopes.example4.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class DialogView implements FxmlView<DialogViewModel> {

	@InjectViewModel
	DialogViewModel viewModel;
}
