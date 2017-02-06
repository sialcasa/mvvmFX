package de.saxsys.mvvmfx;


public interface SceneLifecycle {

	/**
	 * This method will be invoked when the View is added to a {@link javafx.scene.Scene}.
	 */
	void onViewAdded();

	/**
	 * This method will be invoked when the View is removed from a {@link javafx.scene.Scene}.
	 */
	void onViewRemoved();

}
