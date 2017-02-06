package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc;

import de.saxsys.mvvmfx.SceneLifecycle;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.testingutils.GCVerifier;

public class LifecycleGCTestRootViewModel implements ViewModel, SceneLifecycle {

	public static int onViewAddedCalled = 0;
	public static int onViewRemovedCalled = 0;

	@Override
	public void onViewAdded() {
		onViewAddedCalled++;
	}

	@Override
	public void onViewRemoved() {
		onViewRemovedCalled++;
		GCVerifier.forceGC();
	}
}
