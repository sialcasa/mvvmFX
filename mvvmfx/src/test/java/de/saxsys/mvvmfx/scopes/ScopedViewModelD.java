package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope1;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope2;

public class ScopedViewModelD implements ViewModel {

    @InjectScope
    public TestScope1 injectedScope1;

    @InjectScope
    public TestScope2 injectedScope2;

}
