/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
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
package de.saxsys.mvvmfx.utils.notifications;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import de.saxsys.mvvmfx.ViewModel;

/**
 * @author manuel.mauky
 */
public class NotificationTestHelperTest {
	
	public class MyViewModel implements ViewModel {
	}
	
	private MyViewModel viewModel;
	
	@Before
	public void setUp() throws Exception {
		viewModel = new MyViewModel();
	}
	
	@Test
	public void singlePublish() {
		NotificationTestHelper helper = new NotificationTestHelper();
		viewModel.subscribe("test", helper);
		
		viewModel.publish("test");
		
		assertThat(helper.numberOfReceivedNotifications()).isEqualTo(1);
	}
	
	@Test
	public void multiplePublish() {
		NotificationTestHelper helper = new NotificationTestHelper();
		viewModel.subscribe("test", helper);
		
		int n = 10;
		
		for (int i = 0; i < n; i++) {
			viewModel.publish("test");
		}
		
		assertThat(helper.numberOfReceivedNotifications()).isEqualTo(n);
	}
	
	@Test
	public void globalNotificationCenter() {
		NotificationTestHelper helper = new NotificationTestHelper();
		
		NotificationCenter notificationCenter = new DefaultNotificationCenter();
		
		notificationCenter.subscribe("OK", helper);
		
		
		notificationCenter.publish("OK");
		
		
		assertThat(helper.numberOfReceivedNotifications()).isEqualTo(1);
	}
	
	@Test
	public void publishOnOtherThread() {
		NotificationTestHelper helper = new NotificationTestHelper(50l);
		
		NotificationCenter notificationCenter = new DefaultNotificationCenter();
		
		notificationCenter.subscribe("OK", helper);
		
		
		Runnable r = () -> notificationCenter.publish("OK");
		
		new Thread(r).start();
		
		assertThat(helper.numberOfReceivedNotifications()).isEqualTo(1);
	}
	
	@Test
	public void timeout() {
		NotificationTestHelper helper = new NotificationTestHelper(300l);
		
		NotificationCenter notificationCenter = new DefaultNotificationCenter();
		
		notificationCenter.subscribe("OK", helper);
		
		
		Runnable r = () -> {
			try {
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			notificationCenter.publish("OK");
		};
		
		new Thread(r).start();
		
		assertThat(helper.numberOfReceivedNotifications()).isEqualTo(1);
	}
}
