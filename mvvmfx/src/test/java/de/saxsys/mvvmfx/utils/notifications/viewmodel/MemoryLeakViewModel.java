package de.saxsys.mvvmfx.utils.notifications.viewmodel;

import de.saxsys.mvvmfx.ViewModel;

public class MemoryLeakViewModel implements ViewModel {

	public static final String MESSAGE_NAME = "test";


	public void actionThatPublishes() {
		publish(MESSAGE_NAME);
	}
}
