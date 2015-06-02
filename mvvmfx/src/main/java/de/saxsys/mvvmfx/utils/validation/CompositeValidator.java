package de.saxsys.mvvmfx.utils.validation;

import javafx.beans.binding.BooleanExpression;
import javafx.collections.ObservableList;

/**
 * @author manuel.mauky
 */
public class CompositeValidator {

    public void registerValidator(Validator validator) {

    }

	public void registerValidator(Validator... validator){
		
	}
	
	
	public final BooleanExpression validProperty(){
		return null;
	}
	
	public final ObservableList<ValidationResult> resultsProperty(){
		return null;	
	}
	
}
