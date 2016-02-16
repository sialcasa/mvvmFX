package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

import static de.saxsys.mvvmfx.examples.contacts.ui.editcontact.EditContactDialogViewModel.TITLE_LABEL_KEY;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import org.junit.Before;

import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.ContactDialogScope;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class EditContactDialogViewModelTest {
	
	private EditContactDialogViewModel viewModel;
	
	private Repository repository;
	
	private ContactDialogViewModel contactDialogViewModel;
	
	private ContactDialogScope scope;
	
	@Before
	public void setup() {
		scope = new ContactDialogScope();
		
		
		// sadly the ResourceBundle.getString method is final so we can't use mockito
		ResourceBundle resourceBundle = new ListResourceBundle() {
			@Override
			protected Object[][] getContents() {
				return new Object[][] {
						{ TITLE_LABEL_KEY, "default_subdivision_label" }
				};
			}
		};
		
		viewModel = new EditContactDialogViewModel();
		viewModel.dialogScope = scope;
		
		viewModel.defaultResourceBundle = resourceBundle;
		
		repository = mock(Repository.class);
		viewModel.repository = repository;
		
		contactDialogViewModel = mock(ContactDialogViewModel.class);
		when(contactDialogViewModel.validProperty()).thenReturn(new SimpleBooleanProperty(true));
		
		when(contactDialogViewModel.titleTextProperty()).thenReturn(new SimpleStringProperty());
		
	}
	
}
