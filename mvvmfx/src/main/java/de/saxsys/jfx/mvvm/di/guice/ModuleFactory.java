package de.saxsys.jfx.mvvm.di.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

/**
 * This class can be used to create inline {@link Module}s for Guice DI.
 * 
 * @author manuel.mauky
 * 
 */
class ModuleFactory {

	/**
	 * Create a Module with a binding of the given interface to the given
	 * implementation type.
	 * 
	 * @param from
	 *            the type of the interface.
	 * @param to
	 *            the type of the implementation.
	 * @return the module.
	 */
	static <T> Module createModule(final Class<T> from,
			final Class<? extends T> to) {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(from).to(to);
			}
		};
	}

}
