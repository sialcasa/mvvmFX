package de.saxsys.mvvmfx.utils.validation;

/**
 * @author manuel.mauky
 */
public class ValidationMessage {

	private final String message;
	
	private final Severity severity;

	ValidationMessage(Severity severity, String message) {
		this.message = message;
		this.severity = severity;
	}


	public static ValidationMessage warning(String message) {
		return new ValidationMessage(Severity.WARNING, message);
	}
	
	public static ValidationMessage error(String message) {
		return new ValidationMessage(Severity.ERROR, message);
	}
	
	public String getMessage() {
		return message;
	}

	public Severity getSeverity() {
		return severity;
	}

    @Override
    public String toString() {
        return "ValidationMessage{" +
                "message='" + message + '\'' +
                ", severity=" + severity +
                '}';
    }
}