package de.saxsys.mvvmfx.contacts.ui.addressform;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public class AddressFormViewModel implements ViewModel {
	private ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper(true);

	public ReadOnlyBooleanProperty validProperty() {
		return valid.getReadOnlyProperty();
	}


}
