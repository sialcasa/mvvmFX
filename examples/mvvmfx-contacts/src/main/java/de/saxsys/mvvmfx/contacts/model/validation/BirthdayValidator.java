package de.saxsys.mvvmfx.contacts.model.validation;

import de.saxsys.mvvmfx.contacts.util.CentralClock;
import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

import java.time.LocalDate;

public class BirthdayValidator implements Validator<LocalDate>{
	@Override 
	public ValidationResult apply(Control control, LocalDate newValue) {
		if(newValue != null){
			LocalDate now = LocalDate.now(CentralClock.getClock());

			if(newValue.isAfter(now)){
				return ValidationResult.fromError(control, "The birthday can't be set in the future");
			}
		}
		
		return null;
	}
}
