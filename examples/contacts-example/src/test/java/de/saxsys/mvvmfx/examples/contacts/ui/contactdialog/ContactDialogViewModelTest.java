package de.saxsys.mvvmfx.examples.contacts.ui.contactdialog;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.examples.contacts.ui.addressform.AddressFormViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.contactform.ContactFormViewModel;

public class ContactDialogViewModelTest {
	
	private ContactDialogViewModel viewModel;
	
	private ContactFormViewModel contactFormViewModel;
	private AddressFormViewModel addressFormViewModel;
	
	private BooleanProperty contactFormValid = new SimpleBooleanProperty();
	private BooleanProperty addressFormValid = new SimpleBooleanProperty();
	
	@Before
	public void setup() {
		
		contactFormViewModel = mock(ContactFormViewModel.class);
		when(contactFormViewModel.validProperty()).thenReturn(contactFormValid);
		
		addressFormViewModel = mock(AddressFormViewModel.class);
		when(addressFormViewModel.validProperty()).thenReturn(addressFormValid);
		
		viewModel = new ContactDialogViewModel();
		viewModel.setContactFormViewModel(contactFormViewModel);
		viewModel.setAddressFormViewModel(addressFormViewModel);
	}
	
	@Test
	public void testValid() {
		viewModel = new ContactDialogViewModel();
		
		addressFormValid.set(true);
		contactFormValid.set(true);
		
		assertThat(viewModel.validProperty()).isFalse();
		
		viewModel.setContactFormViewModel(contactFormViewModel);
		assertThat(viewModel.validProperty()).isFalse();
		
		viewModel.setAddressFormViewModel(addressFormViewModel);
		assertThat(viewModel.validProperty()).isTrue();
		
		addressFormValid.set(false);
		assertThat(viewModel.validProperty()).isFalse();
		
		addressFormValid.set(true);
		assertThat(viewModel.validProperty()).isTrue();
		
		contactFormValid.set(false);
		assertThat(viewModel.validProperty()).isFalse();
		
		contactFormValid.set(true);
		assertThat(viewModel.validProperty()).isTrue();
	}
	
	@Test
	public void testWorkflow() {
		assertThat(viewModel.dialogPageProperty()).hasValue(0);
		
		contactFormValid.set(false);
		addressFormValid.set(false);
		
		// add button is invisible and disabled
		assertThat(viewModel.okButtonVisibleProperty()).isFalse();
		assertThat(viewModel.okButtonDisabledProperty()).isTrue();
		
		// next button is visible but disabled
		assertThat(viewModel.nextButtonVisibleProperty()).isTrue();
		assertThat(viewModel.nextButtonDisabledProperty()).isTrue();
		
		// previous button is invisible and disabled
		assertThat(viewModel.previousButtonVisibleProperty()).isFalse();
		assertThat(viewModel.previousButtonDisabledProperty()).isTrue();
		
		
		
		// now we enter all mandatory values into the form and it is now valid
		contactFormValid.set(true);
		
		// add button is still invisible and disabled
		assertThat(viewModel.okButtonVisibleProperty()).isFalse();
		assertThat(viewModel.okButtonDisabledProperty()).isTrue();
		
		// next button is visible and now also enabled
		assertThat(viewModel.nextButtonVisibleProperty()).isTrue();
		assertThat(viewModel.nextButtonDisabledProperty()).isFalse();
		
		// previous button is invisible and disabled
		assertThat(viewModel.previousButtonVisibleProperty()).isFalse();
		assertThat(viewModel.previousButtonDisabledProperty()).isTrue();
		
		
		
		// lets go to the next page
		viewModel.nextAction();
		
		assertThat(viewModel.dialogPageProperty()).hasValue(1);
		
		// add button is now visible but still disabled
		assertThat(viewModel.okButtonVisibleProperty()).isTrue();
		assertThat(viewModel.okButtonDisabledProperty()).isTrue();
		
		// next button is invisible and enabled
		assertThat(viewModel.nextButtonVisibleProperty()).isFalse();
		assertThat(viewModel.nextButtonDisabledProperty()).isFalse();
		
		// previous button is now visible but still disabled
		assertThat(viewModel.previousButtonVisibleProperty()).isTrue();
		assertThat(viewModel.previousButtonDisabledProperty()).isTrue();
		
		
		// lets enter valid address informations...
		addressFormValid.set(true);
		
		// add button is still visible and now also enabled
		assertThat(viewModel.okButtonVisibleProperty()).isTrue();
		assertThat(viewModel.okButtonDisabledProperty()).isFalse();
		
		// next button is invisible and enabled
		assertThat(viewModel.nextButtonVisibleProperty()).isFalse();
		assertThat(viewModel.nextButtonDisabledProperty()).isFalse();
		
		// previous button is still visible and now also enabled
		assertThat(viewModel.previousButtonVisibleProperty()).isTrue();
		assertThat(viewModel.previousButtonDisabledProperty()).isFalse();
		
		
		// lets go back to the previous page. The address form is still valid.
		viewModel.previousAction();
		assertThat(viewModel.dialogPageProperty()).hasValue(0);
		
		// add button is invisible again and but still enabled
		assertThat(viewModel.okButtonVisibleProperty()).isFalse();
		assertThat(viewModel.okButtonDisabledProperty()).isFalse();
		
		// next button is visible again and still enabled
		assertThat(viewModel.nextButtonVisibleProperty()).isTrue();
		assertThat(viewModel.nextButtonDisabledProperty()).isFalse();
		
		// previous button is now invisible but stays enabled
		assertThat(viewModel.previousButtonVisibleProperty()).isFalse();
		assertThat(viewModel.previousButtonDisabledProperty()).isFalse();
		
	}
}
