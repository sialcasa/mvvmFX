package de.saxsys.mvvmfx.examples.contacts.model.validation;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import de.saxsys.mvvmfx.examples.contacts.ui.validators.BirthdayValidator;
import de.saxsys.mvvmfx.utils.validation.Validator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.saxsys.mvvmfx.examples.contacts.util.CentralClock;
import de.saxsys.mvvmfx.utils.validation.ValidationStatus;

public class BirthdayValidatorTest {

	private ValidationStatus result;
	private ObjectProperty<LocalDate> value = new SimpleObjectProperty<>();

	@BeforeEach
	public void setup() {
		ZonedDateTime now = ZonedDateTime
				.of(LocalDate.of(2014, Month.JANUARY, 1), LocalTime.of(0, 0), ZoneId.systemDefault());

		CentralClock.setFixedClock(now);

		Validator validator = new BirthdayValidator(value);

		result = validator.getValidationStatus();
	}

	@Test
	public void testBirthdayInThePast() {
		LocalDate now = LocalDate.now(CentralClock.getClock());
		LocalDate birthday = now.minusYears(20);

		value.set(birthday);

		assertThat(result.isValid()).isTrue();
	}

	@Test
	public void testBirthdayInTheFuture() {

		LocalDate now = LocalDate.now(CentralClock.getClock());

		LocalDate birthday = now.plusDays(1);

		value.set(birthday);
		assertThat(result.isValid()).isFalse();
		assertThat(result.getErrorMessages()).hasSize(1);
	}

}
