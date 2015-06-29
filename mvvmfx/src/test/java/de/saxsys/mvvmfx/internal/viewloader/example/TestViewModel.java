package de.saxsys.mvvmfx.internal.viewloader.example;

import de.saxsys.mvvmfx.ViewModel;

public class TestViewModel implements ViewModel {
	public static int instanceCounter = 0;
	
	public TestViewModel() {
		instanceCounter++;
	}
	
}
