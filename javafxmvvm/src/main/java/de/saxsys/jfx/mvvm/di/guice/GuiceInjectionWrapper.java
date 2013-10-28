package de.saxsys.jfx.mvvm.di.guice;

import javax.inject.Inject;

import com.google.inject.Injector;

import de.saxsys.jfx.mvvm.di.InjectionWrapper;

/**
 * Guice specific implementation of the {@link InjectionWrapper}.
 * 
 * @author manuel.mauky
 * 
 */
public class GuiceInjectionWrapper implements InjectionWrapper {

	@Inject
	private Injector injector;

	@Override
	public <T> T getInstance(Class<T> type) {
		return injector.getInstance(type);
	}

}
