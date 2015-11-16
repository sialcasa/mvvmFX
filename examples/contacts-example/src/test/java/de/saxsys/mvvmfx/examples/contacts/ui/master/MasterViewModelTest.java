package de.saxsys.mvvmfx.examples.contacts.ui.master;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.examples.contacts.events.ContactsUpdatedEvent;
import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.ContactFactory;
import de.saxsys.mvvmfx.examples.contacts.model.InmemoryRepository;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.MasterDetailScope;

@SuppressWarnings("unchecked")
public class MasterViewModelTest {
	
	
	private MasterViewModel viewModel;
	private MasterDetailScope mdScope;
	
	private Repository repository;
	private Contact contact1;
	private Contact contact2;
	private Contact contact3;
	
	private Consumer<MasterTableViewModel> onSelectConsumer;
	
	@Before
	public void setup() {
		repository = new InmemoryRepository();
		viewModel = new MasterViewModel();
		mdScope = new MasterDetailScope();
		
		viewModel.mdScope = mdScope;
		viewModel.repository = repository;
		
		contact1 = ContactFactory.createRandomContact();
		contact2 = ContactFactory.createRandomContact();
		contact3 = ContactFactory.createRandomContact();
		
		repository.save(contact1);
		repository.save(contact2);
		repository.save(contact3);
		
		
		onSelectConsumer = mock(Consumer.class);
		viewModel.setOnSelect(onSelectConsumer);
	}
	
	@Test
	public void testSelectContact() {
		viewModel.initialize();
		
		
		assertThat(viewModel.selectedTableRowProperty()).hasNullValue();
		assertThat(mdScope.selectedContactProperty()).hasNullValue();
		
		
		MasterTableViewModel firstRow = viewModel.getContactList().get(0);
		
		viewModel.selectedTableRowProperty().set(firstRow);
		
		assertThat(mdScope.selectedContactProperty()).hasNotNullValue();
		assertThat(mdScope.selectedContactProperty().get().getId()).isEqualTo(firstRow.getId());
		
		
		viewModel.selectedTableRowProperty().set(null);
		
		assertThat(mdScope.selectedContactProperty()).hasNullValue();
	}
	
	
	/**
	 * When no item is selected before an update then after the update still no item should be selected.
	 */
	@Test
	public void testUpdateContactListNoSelection() {
		viewModel.initialize();
		viewModel.selectedTableRowProperty().set(null);
		
		assertThat(getContactIdsInTable()).contains(contact1.getId(), contact2.getId(), contact3.getId());
		
		viewModel.onContactsUpdateEvent(new ContactsUpdatedEvent());
		
		verify(onSelectConsumer, never()).accept(any());
	}
	
	/**
	 * When the contactList is updated and the item that was selected before the update is still available in the
	 * repository (i.e. it wasn't removed) this item should still be selected after the update.
	 */
	@Test
	public void testUpdateContactListSelectionPersistsAfterUpdate() {
		viewModel.initialize();
		assertThat(getContactIdsInTable()).contains(contact1.getId(), contact2.getId(), contact3.getId());
		
		MasterTableViewModel row2 = findTableViewModelForContact(contact2);
		
		viewModel.selectedTableRowProperty().set(row2);
		
		
		repository.delete(contact1); // Not the selected contact
		
		viewModel.onContactsUpdateEvent(new ContactsUpdatedEvent());
		
		
		assertThat(getContactIdsInTable()).contains(contact2.getId(), contact3.getId())
				.doesNotContain(contact1.getId());
				
		verify(onSelectConsumer).accept(row2);
	}
	
	/**
	 * When the contactList is updated and the item that was selected before the update is now not available in the
	 * repository anymore (because it was removed) then no item should be selected.
	 */
	@Test
	public void testUpdateContactListNoSelectionWhenSelectedItemIsRemoved() {
		viewModel.initialize();
		MasterTableViewModel row2 = findTableViewModelForContact(contact2);
		
		viewModel.selectedTableRowProperty().set(row2);
		
		
		repository.delete(contact2); // The selected contact
		
		
		viewModel.onContactsUpdateEvent(new ContactsUpdatedEvent());
		
		
		assertThat(getContactIdsInTable()).contains(contact1.getId(), contact3.getId())
				.doesNotContain(contact2.getId());
				
		verify(onSelectConsumer).accept(null);
	}
	
	
	/**
	 * This helper extracts the IDs of all Contact rows in that are shown in the TableView.
	 * 
	 * The TableView doesn't directly show instances of {@link de.saxsys.mvvmfx.examples.contacts.model.Contact} but
	 * instead contains instances of {@link de.saxsys.mvvmfx.examples.contacts.ui.master.MasterTableViewModel}.
	 * 
	 * Every {@link de.saxsys.mvvmfx.examples.contacts.ui.master.MasterTableViewModel} has an ID attribute corresponding
	 * to the ID of the contact that is shown. This method extracts these IDs and returns them as List. This way we can
	 * verify what Contacts are shown in the Table.
	 */
	private List<String> getContactIdsInTable() {
		return viewModel.getContactList().stream().map(MasterTableViewModel::getId).collect(
				Collectors.toList());
	}
	
	/**
	 * Returns the {@link de.saxsys.mvvmfx.examples.contacts.ui.master.MasterTableViewModel} for the given
	 * {@link de.saxsys.mvvmfx.examples.contacts.model.Contact} from the contact list.
	 */
	private MasterTableViewModel findTableViewModelForContact(Contact contact) {
		return viewModel.getContactList().stream().filter(row -> row.getId().equals(contact.getId())).findFirst().get();
	}
}
