package de.saxsys.mvvmfx.examples.books.backend;

public class Error {
	
	private final String message;
	
	private final String details;
	
	public static Error error(String message, String details) {
		return new Error(message, details);
	}
	
	private Error(String message, String details) {
		this.message = message;
		this.details = details;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDetails() {
		return details;
	}
}
