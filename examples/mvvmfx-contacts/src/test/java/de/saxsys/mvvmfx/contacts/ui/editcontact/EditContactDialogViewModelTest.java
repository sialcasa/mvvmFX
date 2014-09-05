package de.saxsys.mvvmfx.contacts.ui.editcontact;

import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditContactDialogViewModelTest {
	
	private EditContactDialogViewModel viewModel;
	
	private Repository repository;
	
	private ContactFormViewModel contactFormViewModel;
	
	@Before
	public void setup(){
		viewModel = new EditContactDialogViewModel();
		
		repository = mock(Repository.class);
		viewModel.repository = repository;
		
		
		contactFormViewModel = mock(ContactFormViewModel.class);
		when(contactFormViewModel.validProperty()).thenReturn(new SimpleBooleanProperty(true));
		viewModel.initContactFormViewModel(contactFormViewModel);
	}


	@Test
	public void testOpenDialogSuccess(){
		Contact chewie = new Contact();
		chewie.setFirstname("Chewbacca");
		
		when(repository.findById(chewie.getId())).thenReturn(Optional.of(chewie));
		
		
		viewModel.openDialog(chewie.getId());
		
		verify(contactFormViewModel).initWithContact(chewie);
		assertThat(viewModel.dialogOpenProperty()).isTrue();
	}
	
	@Test
	public void testOpenDialogNoSuchContact(){
		when(repository.findById("12345")).thenReturn(Optional.empty());
		
		viewModel.openDialog("12345");
		
		verify(contactFormViewModel, never()).initWithContact(any());
		assertThat(viewModel.dialogOpenProperty()).isFalse();
	}
	
}
