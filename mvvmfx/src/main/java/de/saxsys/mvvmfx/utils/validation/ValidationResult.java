package de.saxsys.mvvmfx.utils.validation;

import java.util.Optional;

/**
 * @author manuel.mauky
 */
public class ValidationResult {
	
	private String message;
	
	private ValidationResult(String message){
		this.message = message;
	}
	
	private ValidationResult(){
	}

	public static ValidationResult error(String message) {
		return new ValidationResult(message);
	}
	
	public static ValidationResult ok(){
		return new ValidationResult();
	}
	
	public boolean isValid(){
		return message == null;
	}
	
	public Optional<String> getMessage(){
		return Optional.ofNullable(message);
	}
}
