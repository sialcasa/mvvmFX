package de.saxsys.jfx.mvvm.utils;

/**
 * Interface declares a cleanable class. When the method is called, the class
 * should clean up all necessary things like listeners (use
 * {@link ListenerCleaner}).
 * 
 * @author sialcasa
 */
public interface ICleanable {
	/**
	 * Clean logic for a component.
	 */
	void clean();
}
