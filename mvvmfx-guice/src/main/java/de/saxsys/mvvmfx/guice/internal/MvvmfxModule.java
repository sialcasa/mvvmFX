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
package de.saxsys.mvvmfx.guice.internal;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;

/**
 * This guice module is used to enable guice integration for classes of the core mvvmFX framework.
 *
 * @author manuel.mauky
 */
public class MvvmfxModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(NotificationCenter.class).toProvider(new Provider<NotificationCenter>() {
			@Override
			public NotificationCenter get() {
				return MvvmFX.getNotificationCenter();
			}
		});
		
		
	}
}
