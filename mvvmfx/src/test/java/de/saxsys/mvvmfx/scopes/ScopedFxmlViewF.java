package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ScopedFxmlViewF implements FxmlView<ScopedViewModelF> {

	@InjectViewModel
	public ScopedViewModelF viewModel;


	public ScopedFxmlViewF() {
		System.out.println("new " + this.getClass().getSimpleName() + "()");
	}

	public void initialize() {
		System.out.println(this.getClass().getSimpleName() + ".initialize()");
	}
}
