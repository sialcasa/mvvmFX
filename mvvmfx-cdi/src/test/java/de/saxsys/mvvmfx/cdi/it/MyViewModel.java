package de.saxsys.mvvmfx.cdi.it;

import de.saxsys.mvvmfx.InjectResourceBundle;
import de.saxsys.mvvmfx.ViewModel;

import javax.inject.Inject;

public class MyViewModel implements ViewModel {
	public static int instanceCounter = 0;
	
	@Inject
	private MyService myService;
	
	public MyViewModel() {
		instanceCounter++;
	}
	
}
