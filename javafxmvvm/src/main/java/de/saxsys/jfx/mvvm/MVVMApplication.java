package de.saxsys.jfx.mvvm;

import java.util.List;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Module;

import de.saxsys.jfx.mvvm.notifications.NotificationCenterModule;

/**
 * MVVMApplication which has to be extended to enable the guice support.
 * 
 * @author sialcasa
 */
public abstract class MVVMApplication extends GuiceApplication {
	@Override
	public void init(List<Module> modules) throws Exception {
		modules.add(new NotificationCenterModule());
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
