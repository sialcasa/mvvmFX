package de.saxsys.mvvmfx.internal.viewloader;

import java.util.Arrays;

import de.saxsys.mvvmfx.ViewModel;

public class ScopeHelper {

	public static <T extends ViewModel> T newScope(Class<T> scopeClass, ScopeTarget<T>... scopeTargets) {
		T scopeViewModel = DependencyInjector.getInstance().getInstanceOf(scopeClass);
		newScope(scopeViewModel, scopeTargets);
		return scopeViewModel;
	}

	
	public static <T extends ViewModel> void newScope(T scopeInstance, ScopeTarget<T> ... scopeTargets){
		Arrays.stream(scopeTargets).forEach(target -> target.initializeScope(scopeInstance));
	}
	
}