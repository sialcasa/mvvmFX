package de.saxsys.mvvmfx.examples.contacts.ui.menu;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.events.TriggerShutdownEvent;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.master.MasterViewModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public class MenuViewModel implements ViewModel {
	
	@Inject
	private Event<TriggerShutdownEvent> shouldCloseEvent;
	
	
	@Inject
	private MasterViewModel masterViewModel;
	
	@Inject
	private Repository repository;
	
	private final ReadOnlyBooleanWrapper removeItemDisabled = new ReadOnlyBooleanWrapper();
	
	@PostConstruct
	public void init() {
		removeItemDisabled.bind(masterViewModel.selectedContactProperty().isNull());
	}
	
	public void closeAction() {
		shouldCloseEvent.fire(new TriggerShutdownEvent());
	}
	
	public void removeAction() {
		Contact selectedContact = masterViewModel.selectedContactProperty().get();
		if (selectedContact != null) {
			repository.delete(masterViewModel.selectedContactProperty().get());
		}
	}
	
	public ReadOnlyBooleanProperty removeItemDisabledProperty() {
		return removeItemDisabled.getReadOnlyProperty();
	}
	
}
