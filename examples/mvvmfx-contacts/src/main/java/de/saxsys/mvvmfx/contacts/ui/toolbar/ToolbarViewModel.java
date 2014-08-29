package de.saxsys.mvvmfx.contacts.ui.toolbar;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.events.OpenAddContactPopupEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class ToolbarViewModel implements ViewModel {
	
	@Inject
	private Event<OpenAddContactPopupEvent> openPopupEvent;
	
	public void addNewContactAction(){
		openPopupEvent.fire(new OpenAddContactPopupEvent());
	}
}
