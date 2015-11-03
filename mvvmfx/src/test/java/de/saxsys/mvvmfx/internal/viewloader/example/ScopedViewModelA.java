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
	
	public ScopedViewModelA() {
		scope2 = ScopeStore.getInstance().getScope(TestScope.class, "coolId2");
		scope3 = ScopeStore.getInstance().getScope(TestScope.class, "coolId3");
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
