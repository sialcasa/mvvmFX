package de.saxsys.mvvmfx.examples.contacts.ui.editcontact;

import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.addressform.AddressFormViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.contactdialog.ContactDialogViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.contactform.ContactFormViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.ListResourceBundle;
import java.util.Optional;
import java.util.ResourceBundle;

import static de.saxsys.mvvmfx.examples.contacts.ui.editcontact.EditContactDialogViewModel.TITLE_LABEL_KEY;
import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditContactDialogViewModelTest {
	
	private EditContactDialogViewModel viewModel;
	
	private Repository repository;
	
	private ContactDialogViewModel contactDialogViewModel;
	private ContactFormViewModel contactFormViewModel;
	private AddressFormViewModel addressFormViewModel;
	
	private ResourceBundle resourceBundle;
	
	@Before
	public void setup() {
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
		viewModel.defaultResourceBundle = resourceBundle;
		
		repository = mock(Repository.class);
		viewModel.repository = repository;
		
		
		contactFormViewModel = mock(ContactFormViewModel.class);
		addressFormViewModel = mock(AddressFormViewModel.class);
		
		contactDialogViewModel = mock(ContactDialogViewModel.class);
		when(contactDialogViewModel.validProperty()).thenReturn(new SimpleBooleanProperty(true));
		
		when(contactDialogViewModel.getContactFormViewModel()).thenReturn(contactFormViewModel);
		when(contactDialogViewModel.getAddressFormViewModel()).thenReturn(addressFormViewModel);
		when(contactDialogViewModel.titleTextProperty()).thenReturn(new SimpleStringProperty());
		
		viewModel.setContactDialogViewModel(contactDialogViewModel);
	}
	
	
	@Test
	public void testOpenDialogSuccess() {
		Contact chewie = new Contact();
		chewie.setFirstname("Chewbacca");
		
		when(repository.findById(chewie.getId())).thenReturn(Optional.of(chewie));
		
		
		viewModel.openDialog(chewie.getId());
		
		verify(contactFormViewModel).initWithContact(chewie);
		assertThat(viewModel.dialogOpenProperty()).isTrue();
	}
	
	@Test
	public void testOpenDialogNoSuchContact() {
		when(repository.findById("12345")).thenReturn(Optional.empty());
		
		viewModel.openDialog("12345");
		
		verify(contactFormViewModel, never()).initWithContact(any());
		assertThat(viewModel.dialogOpenProperty()).isFalse();
	}
	
}
