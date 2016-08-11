package de.saxsys.mvvmfx.internal.viewloader.livecycle;

import de.saxsys.mvvmfx.StageLivecycle;
import de.saxsys.mvvmfx.ViewModel;

public class LivecycleTestRootViewModel implements ViewModel, StageLivecycle {

	public int onViewAddedCalled = 0;
	public int onViewRemovedCalled = 0;

	@Override
	public void onViewAdded() {
		onViewAddedCalled++;
	}

	@Override
	public void onViewRemoved() {
		onViewRemovedCalled++;
	}
}
