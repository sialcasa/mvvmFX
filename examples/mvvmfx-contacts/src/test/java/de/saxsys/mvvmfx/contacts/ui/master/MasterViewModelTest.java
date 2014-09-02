package de.saxsys.mvvmfx.contacts.ui.master;

import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.model.ContactFactory;
import de.saxsys.mvvmfx.contacts.model.InmemoryRepository;
import de.saxsys.mvvmfx.contacts.model.Repository;
import org.junit.Before;
import org.junit.Test;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

public class MasterViewModelTest {
	
	
	private MasterViewModel viewModel;
	
	private Repository repository;
	
	
	@Before
	public void setup(){
		repository = new InmemoryRepository();
		viewModel = new MasterViewModel();
		viewModel.repository = repository;
	}
	
	@Test
	public void testSelectContact(){
		Contact contact1 = ContactFactory.createRandomContact();
		Contact contact2 = ContactFactory.createRandomContact();
		Contact contact3 = ContactFactory.createRandomContact();
		
		repository.save(contact1);
		repository.save(contact2);
		repository.save(contact3);
		
		
		
		viewModel.init();
		
		
		assertThat(viewModel.selectedTableRowProperty()).hasNullValue();
		assertThat(viewModel.selectedContactProperty()).hasNullValue();


		MasterTableViewModel firstRow = viewModel.contactList().get(0);
		
		viewModel.selectedTableRowProperty().set(firstRow);

		assertThat(viewModel.selectedContactProperty()).hasNotNullValue();
		assertThat(viewModel.selectedContactProperty().get().getId()).isEqualTo(firstRow.getId());

		
		viewModel.selectedTableRowProperty().set(null);
		
		assertThat(viewModel.selectedContactProperty()).hasNullValue();
	}
	
	
}
