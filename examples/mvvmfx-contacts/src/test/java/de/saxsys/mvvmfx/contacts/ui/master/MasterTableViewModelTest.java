package de.saxsys.mvvmfx.contacts.ui.master;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import de.saxsys.mvvmfx.contacts.model.Contact;
import de.saxsys.mvvmfx.contacts.util.CentralClock;

public class MasterTableViewModelTest {
	
	
	@Test
	public void testCalculationOfAge() {
		
		ZonedDateTime now = ZonedDateTime
				.of(LocalDate.of(2010, Month.JANUARY, 1), LocalTime.of(0, 0), ZoneId.systemDefault()); // 2010-01-01T00:00
		
		CentralClock.setFixedClock(now);
		
		
		Contact contact = new Contact();
		contact.setBirthday(LocalDate.of(1987, Month.DECEMBER, 13));
		
		MasterTableViewModel tableViewModel = new MasterTableViewModel(contact);
		
		assertThat(tableViewModel.ageProperty().get()).isEqualTo(22);
	}
	
}
