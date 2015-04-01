package de.saxsys.mvvmfx.contacts.ui.toolbar;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.events.OpenAddContactDialogEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;

public class ToolbarViewModel implements ViewModel {
	
	@Inject
	private Event<OpenAddContactDialogEvent> openPopupEvent;
	
	public void addNewContactAction() {
		openPopupEvent.fire(new OpenAddContactDialogEvent());
	}
}
