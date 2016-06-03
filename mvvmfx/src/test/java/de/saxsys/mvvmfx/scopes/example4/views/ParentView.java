package de.saxsys.mvvmfx.scopes.example4.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ParentView implements FxmlView<ParentViewModel> {

	@InjectViewModel
	ParentViewModel viewModel;
}
