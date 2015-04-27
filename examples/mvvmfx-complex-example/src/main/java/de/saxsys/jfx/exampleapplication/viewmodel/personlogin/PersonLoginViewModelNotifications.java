package de.saxsys.jfx.exampleapplication.viewmodel.personlogin;

public enum PersonLoginViewModelNotifications {
	OK("Das Einloggen war erfolgreich");
	
	private String message;
	
	PersonLoginViewModelNotifications(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getId() {
		return toString();
	}
}
