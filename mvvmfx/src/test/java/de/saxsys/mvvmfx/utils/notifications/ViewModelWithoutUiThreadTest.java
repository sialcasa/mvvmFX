package de.saxsys.mvvmfx.utils.notifications;

import de.saxsys.mvvmfx.ViewModel;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This test verifies the behaviour of the publish/subscribe mechanism of ViewModels when no JavaFX thread is running.
 * In this case the publish/subscribe should still be working.
 * 
 * @author manuel.mauky
 */
public class ViewModelWithoutUiThreadTest {
	
	public static class MyViewModel implements ViewModel {
	}
	
	
	@Test
	public void test() throws InterruptedException, ExecutionException, TimeoutException {
		
		MyViewModel viewModel = new MyViewModel();
		
		CompletableFuture<Void> future = new CompletableFuture<>();
		
		viewModel.subscribe("test", (key, payload) -> {
			future.complete(null);
		});
		
		
		viewModel.publish("test");
		
		future.get(1l, TimeUnit.SECONDS);
	}
}
