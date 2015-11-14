package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

import java.util.ResourceBundle;

import javax.inject.Inject;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;

public class EditContactDialogViewModel implements ViewModel {
	
	static final String TITLE_LABEL_KEY = "dialog.editcontact.title";
	public static final String CLOSE_DIALOG_NOTIFICATION = "CLOSE_DIALOG_NOT";
	
	@Inject
	Repository repository;
	
	@InjectScope
	ContactDialogScope dialogScope;
	
	@Inject
	ResourceBundle defaultResourceBundle;
	
	public void initialize() {
		dialogScope.publish(ContactDialogScope.RESET_FORMS);
		dialogScope.publish(ContactDialogScope.RESET_DIALOG_PAGE);
		dialogScope.subscribe(ContactDialogScope.OK_BEFORE_COMMIT, (key, payload) -> {
			applyAction();
		});
		
		dialogScope.dialogTitleProperty().set(defaultResourceBundle.getString(TITLE_LABEL_KEY));
	}
	
	public void applyAction() {
		if (dialogScope.bothFormsValidProperty().get()) {
			dialogScope.publish(ContactDialogScope.COMMIT);
			repository.save(dialogScope.contactToEditProperty().get());
			publish(CLOSE_DIALOG_NOTIFICATION);
		}
	}
}
