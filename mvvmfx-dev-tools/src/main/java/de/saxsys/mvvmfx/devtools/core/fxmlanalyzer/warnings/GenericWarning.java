package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.warnings;


public class GenericWarning implements Warning {

	private final String message;

	public GenericWarning(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
