package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ScopeStore;
import de.saxsys.mvvmfx.ViewModel;

/**
 * 
 * @author alexander.casall
 */
public class ScopedViewModelA implements ViewModel {
	
	@InjectScope
	private TestScope scope;
	
	private final TestScope scope2;
	private final TestScope scope3;


    public static boolean check = false;


    private ScopeStore scopeStore = new ScopeStore();

	public ScopedViewModelA() {
		scope2 = scopeStore.getScope(TestScope.class, "coolId2");
		scope3 = scopeStore.getScope(TestScope.class, "coolId3");
	}


    public void initialize() {
        check = true;
        System.out.println(">" + scope.toString());
    }
	
	public TestScope getScope() {
		return scope;
	}
	
	public TestScope getScope2() {
		return scope2;
	}
	
	public TestScope getScope3() {
		return scope3;
	}
	
	
}
