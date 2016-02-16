package de.saxsys.mvvmfx.examples.contacts.ui.detail;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.examples.contacts.model.Contact;
import de.saxsys.mvvmfx.examples.contacts.model.Repository;
import de.saxsys.mvvmfx.examples.contacts.ui.scopes.MasterDetailScope;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DetailViewModelTest {
	
	
	
	private DetailViewModel viewModel;
	
	private final ObjectProperty<Contact> selectedContact = new SimpleObjectProperty<>();
	private Contact luke;
	private Contact obi;
	
	private Repository repository;
	
	@Before
	public void setup() {
		MasterDetailScope masterViewModelMock = mock(MasterDetailScope.class);
		
		when(masterViewModelMock.selectedContactProperty()).thenReturn(selectedContact);
		
		viewModel = new DetailViewModel();
		viewModel.mdScope = masterViewModelMock;
		
		repository = mock(Repository.class);
		viewModel.repository = repository;
		
		viewModel.initialize();
		
		luke = new Contact();
		obi = new Contact();
	}
	
	
	@Test
	public void testRemoveAction() {
		selectedContact.set(null);
		assertThat(viewModel.getRemoveCommand().notExecutableProperty()).isTrue();
		selectedContact.set(luke);
		assertThat(viewModel.getRemoveCommand().notExecutableProperty()).isFalse();
		viewModel.getRemoveCommand().execute();
		verify(repository).delete(luke);
	}
	
	@Test
	public void testNameLabelText() {
		luke.setFirstname("Luke");
		luke.setLastname("Skywalker");
		
		obi.setFirstname("Obi-Wan");
		obi.setLastname("Kenobi");
		obi.setTitle("Master");
		
		selectedContact.set(null);
		
		assertThat(viewModel.nameLabelTextProperty()).hasValue("");
		
		selectedContact.set(luke);
		
		assertThat(viewModel.nameLabelTextProperty()).hasValue("Luke Skywalker");
		
		
		selectedContact.set(obi);
		
		assertThat(viewModel.nameLabelTextProperty()).hasValue("Master Obi-Wan Kenobi");
	}
	
	
	@Test
	public void testBirthdayLabelText() {
		luke.setBirthday(LocalDate.of(1951, 9, 25));
		obi.setBirthday(null);
		
		selectedContact.set(null);
		assertThat(viewModel.birthdayLabelTextProperty()).hasValue("");
		
		selectedContact.set(luke);
		assertThat(viewModel.birthdayLabelTextProperty()).hasValue("1951-09-25");
		
		selectedContact.set(obi);
		assertThat(viewModel.birthdayLabelTextProperty()).hasValue("");
		
	}
	
	@Test
	public void testRoleDepartmentLabelText() {
		luke.setRole("Pilot");
		luke.setDepartment("Rebel Alliance");
		
		obi.setRole("Jedi");
		obi.setDepartment(null);
		
		selectedContact.set(null);
		assertThat(viewModel.roleDepartmentLabelTextProperty()).hasValue("");
		
		selectedContact.set(luke);
		assertThat(viewModel.roleDepartmentLabelTextProperty()).hasValue("Pilot / Rebel Alliance");
		
		selectedContact.set(obi);
		assertThat(viewModel.roleDepartmentLabelTextProperty()).hasValue("Jedi");
		
		
		luke.setRole(null); // the galactic war is over now so he isn't a pilot anymore ;-)
		
		selectedContact.set(luke);
		assertThat(viewModel.roleDepartmentLabelTextProperty()).hasValue("Rebel Alliance");
		
		
		obi.setRole("");
		obi.setDepartment("");
		
		selectedContact.set(obi);
		assertThat(viewModel.roleDepartmentLabelTextProperty()).hasValue("");
	}
	
	@Test
	public void testEmailLabelText() {
		luke.setEmailAddress("luke@rebel-alliance.com");
		
		obi.setEmailAddress(null);
		
		
		selectedContact.set(null);
		assertThat(viewModel.emailLabelTextProperty()).hasValue("");
		
		selectedContact.set(luke);
		assertThat(viewModel.emailLabelTextProperty()).hasValue("luke@rebel-alliance.com");
		
		selectedContact.set(obi);
		assertThat(viewModel.emailLabelTextProperty()).hasValue("");
	}
	
	@Test
	public void testPhoneLabelText() {
		luke.setPhoneNumber(null);
		obi.setPhoneNumber("0123456789");
		
		selectedContact.set(null);
		assertThat(viewModel.phoneLabelTextProperty()).hasValue("");
		
		selectedContact.set(luke);
		assertThat(viewModel.phoneLabelTextProperty()).hasValue("");
		
		selectedContact.set(obi);
		assertThat(viewModel.phoneLabelTextProperty()).hasValue("0123456789");
		
		luke.setPhoneNumber("+49 123 456 789");
		selectedContact.set(luke);
		assertThat(viewModel.phoneLabelTextProperty()).hasValue("+49 123 456 789");
	}
	
	@Test
	public void testMobileLabelText() {
		luke.setMobileNumber(null);
		obi.setMobileNumber("0123456789");
		
		selectedContact.set(null);
		assertThat(viewModel.mobileLabelTextProperty()).hasValue("");
		
		selectedContact.set(luke);
		assertThat(viewModel.mobileLabelTextProperty()).hasValue("");
		
		selectedContact.set(obi);
		assertThat(viewModel.mobileLabelTextProperty()).hasValue("0123456789");
		
		luke.setMobileNumber("+49 123 456 789");
		selectedContact.set(luke);
		assertThat(viewModel.mobileLabelTextProperty()).hasValue("+49 123 456 789");
	}
}
