package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.ViewModel;

public class TestViewModel implements ViewModel {
	public static int instanceCounter = 0;

    public static boolean wasInitialized = false;
	
	public TestViewModel() {
		instanceCounter++;
	}

    public void initialize() {
        wasInitialized = true;
    }
	
}
