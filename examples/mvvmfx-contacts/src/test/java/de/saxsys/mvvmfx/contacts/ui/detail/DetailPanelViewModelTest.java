package de.saxsys.mvvmfx.contacts.ui.detail;

import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.master.MasterViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Before;
import org.junit.Test;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailPanelViewModelTest {
	
	private DetailPanelViewModel viewModel;

	private Repository repository;

	private ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>();
	private Contact luke;
	private Contact obi;
	
	@Before
	public void setup(){
		viewModel = new DetailPanelViewModel();
		
		
		MasterViewModel masterViewModelMock = mock(MasterViewModel.class);
		viewModel.masterViewModel = masterViewModelMock;
		when(masterViewModelMock.selectedContactProperty()).thenReturn(selectedContact);
		
		
		repository = mock(Repository.class);
		viewModel.repository = repository;

		viewModel.init();

		luke = new Contact();
		obi = new Contact();
	}
	
	
	@Test
	public void testRemoveAction(){
		selectedContact.set(null);
		assertThat(viewModel.removeButtonEnabledProperty()).isFalse();
		
		
		selectedContact.set(luke);
		assertThat(viewModel.removeButtonEnabledProperty()).isTrue();
		
		
		
		viewModel.removeAction();
		
		verify(repository).delete(luke);
	}
	
	
	
	
}
