/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.mvvmfx.cdi.internal;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import javafx.application.Application;
import javafx.application.HostServices;


/**
 * This class collects CDI producer methods to be able to inject classes of the mvvmFX framework. This is needed as the
 * core mvvmFX library isn't enabled for CDI (no beans.xml). An additional reasons for this class are that for some
 * interfaces there may be more than one implementation or there is only a factory to create an instances for a specific
 * class/interface. In both cases a solution is to create producers.
 *
 * @author manuel.mauky
 */
@Singleton
public class MvvmfxProducer {



	private HostServices hostServices;

	@Produces
	public NotificationCenter produceNotificationCenter() {
		return MvvmFX.getNotificationCenter();
	}
	
	@Produces
	public ViewLoader produceViewLoader() {
		return new ViewLoader();
	}

	/**
	 * The {@link javafx.application.HostServices} instance is only available
	 * in the application class. Therefore it needs to be set from there to be 
	 * available for injection.
	 * 
	 * @param hostServices the instance of hostServices from the Application.
	 */
	public void setHostServices(HostServices hostServices){
		this.hostServices = hostServices;
	}
	
	@Produces
	public HostServices produceHostServices(){
		return hostServices;
	}
}
