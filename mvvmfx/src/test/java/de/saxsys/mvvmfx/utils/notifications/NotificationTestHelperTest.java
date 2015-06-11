package de.saxsys.mvvmfx.utils.notifications;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.mvvmfx.ViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

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
	public void singlePublish () {
		NotificationTestHelper helper = new NotificationTestHelper();
		viewModel.subscribe("test", helper);
		
		viewModel.publish("test");
		
		assertThat(helper.numberOfCalls()).isEqualTo(1);
	}
	
	@Test
	public void multiplePublish() {
		NotificationTestHelper helper = new NotificationTestHelper();
		viewModel.subscribe("test", helper);

		int n = 10;

		for(int i=0 ; i<n; i++) {
			viewModel.publish("test");
		}
		
		assertThat(helper.numberOfCalls()).isEqualTo(n);
	}
}
