package de.saxsys.mvvmfx.contacts.ui.menu;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.events.TriggerShutdownEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class MenuViewModel implements ViewModel {
	
	@Inject
	private Event<TriggerShutdownEvent> shouldCloseEvent;
	
	public void closeAction(){
		shouldCloseEvent.fire(new TriggerShutdownEvent());
	}
}
