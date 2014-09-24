package de.saxsys.mvvmfx.contacts.config;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.util.Locale;
import java.util.ResourceBundle;

@Singleton
public class ResourceProvider {

	/*
	 * Due to the @Produces annotation this resource bundle can be injected in all views.
	 */
	@Produces
	private ResourceBundle defaultResourceBundle = ResourceBundle.getBundle("default", Locale.ENGLISH);

}
