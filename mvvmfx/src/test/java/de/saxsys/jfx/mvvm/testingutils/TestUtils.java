package de.saxsys.jfx.mvvm.testingutils;

public class TestUtils {

	/**
	 * This method returns the root cause of a given Exception. 
	 */
	public static Throwable getRootCause(Exception e){
		Throwable cause = e.getCause();
		while (cause != null) {
			if (cause.getCause() == null) {
				break;
			}
			cause = cause.getCause();
		}
		
		return cause;
	}
}
