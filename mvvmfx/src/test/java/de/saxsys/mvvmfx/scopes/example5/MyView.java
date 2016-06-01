package de.saxsys.mvvmfx.scopes.example5;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class MyView implements FxmlView<MyViewModel> {

	@InjectViewModel
	private MyViewModel viewModel;


}
