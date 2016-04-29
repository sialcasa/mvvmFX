package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope1;

public class ScopedViewModelC implements ViewModel {

    @InjectScope
    public TestScope1 injectedScope1;

}
