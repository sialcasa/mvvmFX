package de.saxsys.mvvmfx.scopes.example3.views.content;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ContentView implements FxmlView<ContentViewModel> {

	@InjectViewModel
	public ContentViewModel viewModel;

}
