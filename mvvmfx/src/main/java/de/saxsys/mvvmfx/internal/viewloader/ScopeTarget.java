package de.saxsys.mvvmfx.internal.viewloader;

import de.saxsys.mvvmfx.ViewModel;

public interface ScopeTarget<T extends ViewModel> {
	void initializeScope(T scopeViewModel);
}
