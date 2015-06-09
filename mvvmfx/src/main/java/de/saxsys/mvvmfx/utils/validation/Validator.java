package de.saxsys.mvvmfx.utils.validation;

/**
 * This interface is implemented by specific validators. Each validator has to
 * provide a reactive {@link ValidationStatus} that's is updated by the validator implementation
 * when the validation is executed (f.e. when the user has changed an input value).
 *
 * @author manuel.mauky
 */
public interface Validator {

    /**
     * Returns the validation status of this validator.
     * The status will be updated when the validator re-validates the inputs of the user.
     *
     * @return the state.
     */
	ValidationStatus getValidationStatus();
	
}
