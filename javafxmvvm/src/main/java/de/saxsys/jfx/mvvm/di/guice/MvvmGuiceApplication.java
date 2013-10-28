package de.saxsys.jfx.mvvm.di.guice;

import java.util.List;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Module;

import de.saxsys.jfx.mvvm.di.guice.modules.FXMLLoaderWrapperModule;
import de.saxsys.jfx.mvvm.di.guice.modules.InjectionWrapperModule;
import de.saxsys.jfx.mvvm.di.guice.modules.NotificationCenterModule;


/**
 * MVVMApplication which has to be extended to enable the guice support.
 * 
 * @author sialcasa
 */
public abstract class MvvmGuiceApplication extends GuiceApplication {
	@Override
	public void init(List<Module> modules) throws Exception {
		modules.add(new NotificationCenterModule());
		modules.add(new FXMLLoaderWrapperModule());
		modules.add(new InjectionWrapperModule());
		initGuiceModules(modules);
	}

	/**
	 * Configure the guice modules.
	 * 
	 * @param modules
	 *            module list
	 * @throws Exception
	 *             exc
	 */
	public abstract void initGuiceModules(List<Module> modules) throws Exception;

}
