package de.saxsys.jfx.mvvm.di.guice.modules;

import com.google.inject.AbstractModule;

import de.saxsys.jfx.mvvm.di.InjectionWrapper;
import de.saxsys.jfx.mvvm.di.guice.GuiceInjectionWrapper;

public class InjectionWrapperModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(InjectionWrapper.class).to(GuiceInjectionWrapper.class);
	}

}
