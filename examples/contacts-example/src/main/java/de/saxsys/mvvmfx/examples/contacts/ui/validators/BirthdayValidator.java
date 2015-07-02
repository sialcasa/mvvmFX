package de.saxsys.mvvmfx.examples.contacts.ui.validators;

import de.saxsys.mvvmfx.examples.contacts.util.CentralClock;
import de.saxsys.mvvmfx.utils.validation.FunctionBasedValidator;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * @author manuel.mauky
 */
public class BirthdayValidator extends FunctionBasedValidator<LocalDate> {
	
	private static final Predicate<LocalDate> birthdayPredicate = date ->
			date == null || date.isBefore(LocalDate.now(CentralClock.getClock()));
	
	public BirthdayValidator(ObservableValue<LocalDate> date) {
		super(date, birthdayPredicate, ValidationMessage.error("Birthday can't be set in the future"));
	}
}
