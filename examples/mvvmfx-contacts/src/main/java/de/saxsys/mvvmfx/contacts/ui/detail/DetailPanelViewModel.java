package de.saxsys.mvvmfx.contacts.ui.detail;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.master.MasterViewModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class DetailPanelViewModel implements ViewModel {

	
	private ReadOnlyBooleanWrapper removeButtonEnabled = new ReadOnlyBooleanWrapper();
	
	@Inject
	MasterViewModel masterViewModel;
	
	@Inject
	Repository repository;
	
	
	@PostConstruct
	void init() {
		removeButtonEnabled.bind(masterViewModel.selectedContactProperty().isNotNull());
	}

	public void editAction() {
		
	}

	public void removeAction() {
		repository.delete(masterViewModel.selectedContactProperty().get());
	}
	
	
	public ReadOnlyBooleanProperty removeButtonEnabledProperty(){
		return removeButtonEnabled.getReadOnlyProperty();
	}

}
