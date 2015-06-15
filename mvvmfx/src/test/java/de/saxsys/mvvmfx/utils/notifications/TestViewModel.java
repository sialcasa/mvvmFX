package de.saxsys.mvvmfx.utils.notifications;

import de.saxsys.mvvmfx.ViewModel;

/**
 * Test viewModel used by {@link TestFxAndNotificationTestHelperTest}.
 * 
 * @author manuel.mauky
 */
public class TestViewModel implements ViewModel {
	
	public void myAction() {
		publish("OK");
	}
	
}
