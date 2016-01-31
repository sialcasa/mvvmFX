package de.saxsys.mvvmfx.examples.contacts.config;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.util.ResourceBundle;

/**
 * A singleton CDI provider that is used to load the resource bundle and provide
 * it for the CDI injection.
 */
@Singleton
public class ResourceProvider {

    /*
     * Due to the @Produces annotation this resource bundle can be injected in all views.
     */
    @Produces
    private ResourceBundle defaultResourceBundle = ResourceBundle.getBundle("default");

}
