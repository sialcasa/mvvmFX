package de.saxsys.mvvmfx.contacts.model.validation;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormViewModel;
import de.saxsys.mvvmfx.contacts.util.CentralClock;
import javafx.scene.control.DatePicker;
import org.assertj.core.internal.cglib.core.Local;
import org.controlsfx.validation.ValidationResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JfxRunner.class)
public class BirthdayValidatorTest {
	
	private BirthdayValidator validator;
	private DatePicker datePicker;
	
	@Before
	public void setup() {
		ZonedDateTime now = ZonedDateTime
				.of(LocalDate.of(2014, Month.JANUARY, 1), LocalTime.of(0, 0), ZoneId.systemDefault());
		
		CentralClock.setFixedClock(now);
		
		validator = new BirthdayValidator();
		datePicker = new DatePicker();
	}
	
	@Test
	public void testBirthdayInThePast() {
		
		LocalDate now = LocalDate.now(CentralClock.getClock());
		
		LocalDate birthday = now.minusYears(20);
		
		ValidationResult result = validator.apply(datePicker, birthday);
		
		assertThat(result).isNull();
	}
	
	@Test
	public void testBirthdayInTheFuture() {
		
		LocalDate now = LocalDate.now(CentralClock.getClock());
		
		LocalDate birthday = now.plusDays(1);
		
		ValidationResult result = validator.apply(datePicker, birthday);
		
		assertThat(result).isNotNull();
		assertThat(result.getErrors()).hasSize(1);
	}
}
