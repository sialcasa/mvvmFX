package de.saxsys.mvvmfx.scopes.example3.views.menu;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class MenuView implements FxmlView<MenuViewModel> {

	@InjectViewModel
	public MenuViewModel viewModel;
}
