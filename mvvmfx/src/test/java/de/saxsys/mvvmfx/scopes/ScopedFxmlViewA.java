package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;


/**
 * This class is used as example View class that uses FXML.
 * 
 * @author alexander.casall
 */
public class ScopedFxmlViewA implements FxmlView<ScopedViewModelA> {
	@InjectViewModel
	public ScopedViewModelA viewModel;
}
