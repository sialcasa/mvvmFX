package de.saxsys.jfx.mvvm.di.guice.modules;

import com.google.inject.AbstractModule;

import de.saxsys.jfx.mvvm.di.FXMLLoaderWrapper;
import de.saxsys.jfx.mvvm.di.guice.GuiceFXMLLoaderWrapper;

public class FXMLLoaderWrapperModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(FXMLLoaderWrapper.class).to(GuiceFXMLLoaderWrapper.class);
	}

}
