package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_basic;

import de.saxsys.mvvmfx.SceneLifecycle;
import de.saxsys.mvvmfx.ViewModel;

public class LifecycleTestSub1ViewModel implements ViewModel, SceneLifecycle {

	public static int onViewAddedCalled = 0;
	public static int onViewRemovedCalled = 0;

	@Override
	public void onViewAdded() {
		onViewAddedCalled++;
	}

	@Override
	public void onViewRemoved() {
		onViewRemovedCalled++;
	}
}
