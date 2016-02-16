package de.saxsys.mvvmfx.examples.contacts.ui.contactdialog;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;

public class ContactDialogViewModel implements ViewModel {
	
	@InjectScope
	ContactDialogScope dialogScope;
	
	private final IntegerProperty dialogPage = new SimpleIntegerProperty(0);
	private final ReadOnlyBooleanWrapper valid = new ReadOnlyBooleanWrapper();
	private final StringProperty titleText = new SimpleStringProperty();
	
	public void initialize() {
		valid.bind(
				Bindings.and(dialogScope.contactFormValidProperty(), dialogScope.addressFormValidProperty()));
		dialogScope.bothFormsValidProperty().bind(valid);
		dialogScope.subscribe(ContactDialogScope.RESET_DIALOG_PAGE,
				(key, payload) -> resetDialogPage());
		titleText.bind(dialogScope.dialogTitleProperty());
	}
	
	public void okAction() {
		dialogScope.publish(ContactDialogScope.OK_BEFORE_COMMIT);
	}
	
	public void previousAction() {
		if (dialogPage.get() == 1) {
			dialogPage.set(0);
		}
	}
	
	public void nextAction() {
		if (dialogPage.get() == 0) {
			dialogPage.set(1);
		}
	}
	
	private void resetDialogPage() {
		dialogPage.set(0);
	}
	
	public IntegerProperty dialogPageProperty() {
		return dialogPage;
	}
	
	
	public ObservableBooleanValue okButtonDisabledProperty() {
		return valid.not();
	}
	
	public ObservableBooleanValue okButtonVisibleProperty() {
		return dialogPage.isEqualTo(1);
	}
	
	public ObservableBooleanValue nextButtonDisabledProperty() {
		return dialogScope.contactFormValidProperty().not();
	}
	
	public ObservableBooleanValue nextButtonVisibleProperty() {
		return dialogPage.isEqualTo(0);
	}
	
	public ObservableBooleanValue previousButtonVisibleProperty() {
		return dialogPage.isEqualTo(1);
	}
	
	public ObservableBooleanValue previousButtonDisabledProperty() {
		return dialogScope.addressFormValidProperty().not();
	}
	
	public ReadOnlyBooleanProperty validProperty() {
		return valid;
	}
	
	public StringProperty titleTextProperty() {
		return titleText;
	}
}
