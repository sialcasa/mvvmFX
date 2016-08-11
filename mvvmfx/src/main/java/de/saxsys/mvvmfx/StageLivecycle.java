package de.saxsys.mvvmfx;


public interface StageLivecycle {

	/**
	 * This method will be invoked when the View is added to a {@link javafx.stage.Stage}.
	 */
	void onViewAdded();

	/**
	 * This method will be invoked when the View is removed from a {@link javafx.stage.Stage}.
	 */
	void onViewRemoved();

}
