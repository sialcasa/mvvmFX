package de.saxsys.mvvmfx.contacts.model.validation;

import de.saxsys.mvvmfx.contacts.util.CentralClock;
import de.saxsys.mvvmfx.utils.validation.Rules;
import de.saxsys.mvvmfx.utils.validation.ValidationMessage;
import de.saxsys.mvvmfx.utils.validation.RuleBasedValidator;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * @author manuel.mauky
 */
public class BirthdayValidator extends RuleBasedValidator {
	
	private static final Predicate<LocalDate> birthdayPredicate = date ->
				date == null || date.isBefore(LocalDate.now(CentralClock.getClock()));
	
	public BirthdayValidator(ObservableValue<LocalDate> date) {
		addRule(Rules.fromPredicate(date, birthdayPredicate), ValidationMessage.error("Birthday can't be set in the future"));
	}

}
