package de.saxsys.mvvmfx.scopes.example1.views;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ScopedFxmlViewG implements FxmlView<ScopedViewModelG> {


	@InjectViewModel
	public ScopedViewModelG viewModel;


	public ScopedFxmlViewG() {
		System.out.println("new " + this.getClass().getSimpleName() + "()");
	}

	public void initialize() {
		System.out.println(this.getClass().getSimpleName() + ".initialize()");
	}
}
