package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeStore;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.example.TestScope;

/**
 * 
 * @author alexander.casall
 *		
 */
public class ScopedViewModelB implements ViewModel {

    @InjectScope
    public TestScope injectedScope1;

    @InjectScope("coolId2")
    public TestScope injectedScope2;

    @InjectScope("coolId3")
    public TestScope injectedScope3;

    public final TestScope lazyScope1;
    public final TestScope lazyScope2;
    public final TestScope lazyScope3;


    private ScopeStore scopeStore = new ScopeStore();

    public ScopedViewModelB () {
        lazyScope1 = scopeStore.getScope(TestScope.class);
        lazyScope2 = scopeStore.getScope(TestScope.class, "coolId2");
        lazyScope3 = scopeStore.getScope(TestScope.class, "coolId3");
    }

}
