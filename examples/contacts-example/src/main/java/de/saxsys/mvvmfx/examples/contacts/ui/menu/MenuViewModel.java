package de.saxsys.mvvmfx.examples.contacts.ui.menu;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.events.TriggerShutdownEvent;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.MasterDetailScope;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public class MenuViewModel implements ViewModel {
	
	@Inject
	private Event<TriggerShutdownEvent> shouldCloseEvent;
	
	@InjectScope
	private MasterDetailScope mdScope;
	
	@Inject
	private Repository repository;
	
	private final ReadOnlyBooleanWrapper removeItemDisabled = new ReadOnlyBooleanWrapper();
	
	
	public void initialize() {
		removeItemDisabled.bind(mdScope.selectedContactProperty().isNull());
	}
	
	public void closeAction() {
		shouldCloseEvent.fire(new TriggerShutdownEvent());
	}
	
	public void removeAction() {
		Contact selectedContact = mdScope.selectedContactProperty().get();
		if (selectedContact != null) {
			repository.delete(mdScope.selectedContactProperty().get());
		}
	}
	
	public ReadOnlyBooleanProperty removeItemDisabledProperty() {
		return removeItemDisabled.getReadOnlyProperty();
	}
	
}
