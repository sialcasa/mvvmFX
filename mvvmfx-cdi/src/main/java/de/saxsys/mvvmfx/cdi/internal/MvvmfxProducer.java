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

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;


/**
 * This class collects CDI producer methods to be able to inject classes of the mvvmFX framework. This is needed as the
 * core mvvmFX library isn't enabled for CDI (no beans.xml). An additional reasons for this class are that for some
 * interfaces there may be more than one implementation or there is only a factory to create an instances for a specific
 * class/interface. In both cases a solution is to create producers.
 *
 * @author manuel.mauky
 */
class MvvmfxProducer {
	
	@Produces
	public NotificationCenter produceNotificationCenter() {
		return MvvmFX.getNotificationCenter();
	}
	
	@Produces
	public ViewLoader produceViewLoader() {
		return new ViewLoader();
	}
	
	
}
