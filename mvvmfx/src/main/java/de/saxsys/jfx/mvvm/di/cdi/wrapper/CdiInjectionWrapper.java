package de.saxsys.jfx.mvvm.di.cdi.wrapper;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import de.saxsys.jfx.mvvm.di.InjectionWrapper;

/**
 * CDI specific implementation of the {@link InjectionWrapper}.
 * 
 * @author manuel.mauky
 * 
 */
public class CdiInjectionWrapper implements InjectionWrapper {

	@Inject
	private Instance<Object> instance;

	@Override
	public <T> T getInstance(Class<T> type) {
		return instance.select(type).get();
	}
}
