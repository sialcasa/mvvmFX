package de.saxsys.mvvmfx.testingutils;

public class ExceptionUtils {
	
	/**
	 * This method returns the root cause of a given Exception.
	 * 
	 * @param e
	 *            the exception from that the root cause is extracted.
	 * @return the root cause of the given exception.
	 */
	public static Throwable getRootCause(Exception e) {
		Throwable cause = e;
		
		while (cause != null) {
			if (cause.getCause() == null) {
				break;
			}
			cause = cause.getCause();
		}
		
		return cause;
	}
}
