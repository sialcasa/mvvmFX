package de.saxsys.mvvmfx.scopes.example1.views;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.scopes.example1.Example1Scope1;
import de.saxsys.mvvmfx.scopes.example1.Example1Scope2;

public class ScopedViewModelD implements ViewModel {

    @InjectScope
    public Example1Scope1 injectedScope1;

    @InjectScope
    public Example1Scope2 injectedScope2;

}
