package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_basic;

import de.saxsys.mvvmfx.SceneLivecycle;
import de.saxsys.mvvmfx.ViewModel;

public class LivecycleTestSub1ViewModel implements ViewModel, SceneLivecycle {

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
