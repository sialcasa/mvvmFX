package de.saxsys.mvvmfx.utils.notifications;

import de.saxsys.mvvmfx.ViewModel;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
