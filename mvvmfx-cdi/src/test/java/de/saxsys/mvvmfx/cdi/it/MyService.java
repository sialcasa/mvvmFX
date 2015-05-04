package de.saxsys.mvvmfx.cdi.it;

public class MyService {
	public static int instanceCounter = 0;
	
	public MyService() {
		instanceCounter++;
	}
	
}
