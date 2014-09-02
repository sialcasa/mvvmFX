package de.saxsys.mvvmfx.contacts.ui.detail;

import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.ui.master.MasterViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.Before;
import org.junit.Test;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DetailViewModelTest {
	
	
	
	private DetailViewModel viewModel;
	
	private ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>();
	
	@Before
	public void setup(){
		MasterViewModel masterViewModelMock = mock(MasterViewModel.class);	
		
		when(masterViewModelMock.selectedContactProperty()).thenReturn(selectedContact);
		
		viewModel = new DetailViewModel();
		viewModel.masterViewModel = masterViewModelMock;
		
		viewModel.init();
	}
	
	
	@Test
	public void testNameProperty(){
		
		Contact luke = new Contact();
		luke.setFirstname("Luke");
		luke.setLastname("Skywalker");
	
		
		Contact obi = new Contact();
		obi.setFirstname("Obi-Wan");
		obi.setLastname("Kenobi");
		obi.setTitle("Master");
		
		selectedContact.set(null);
		
		assertThat(viewModel.nameProperty()).hasValue("");
		
		selectedContact.set(luke);
		
		assertThat(viewModel.nameProperty()).hasValue("Luke Skywalker");
		
		
		selectedContact.set(obi);
		
		assertThat(viewModel.nameProperty()).hasValue("Master Obi-Wan Kenobi");
	}
	
}
