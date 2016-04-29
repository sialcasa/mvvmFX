package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class ScopedFxmlViewD implements FxmlView<ScopedViewModelD> {

    @InjectViewModel
    ScopedViewModelD viewModel;

}
