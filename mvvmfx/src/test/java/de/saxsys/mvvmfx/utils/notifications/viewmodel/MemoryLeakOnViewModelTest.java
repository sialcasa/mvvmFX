package de.saxsys.mvvmfx.utils.notifications.viewmodel;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.testingutils.GCVerifier;
import de.saxsys.mvvmfx.testingutils.jfxrunner.JfxRunner;
import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;
import javafx.application.Platform;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * This test verifies that the communication between View and ViewModel
 * via notifications doesn't introduce memory leaks.
 */
@RunWith(JfxRunner.class)
public class MemoryLeakOnViewModelTest {


	/**
	 * A more complex and realistic test to verify that both
	 * the View and the ViewModel can be garbage collected.
	 */
	@Test
	public void testViewModelWithViewCommunication() {

		MemoryLeakViewModel viewModel = new MemoryLeakViewModel();

		MemoryLeakView view = new MemoryLeakView();
		view.viewModel = viewModel;

		view.init();

		GCVerifier.forceGC();

		viewModel.actionThatPublishes();

		waitForUiThread();


		assertThat(view.counter.get()).isEqualTo(1);

		GCVerifier viewVerifier = GCVerifier.create(view);
		GCVerifier viewModelVerifier = GCVerifier.create(viewModel);


		viewModel = null;
		view = null;


		viewModelVerifier.verify("ViewModel cannot be GCed");
		viewVerifier.verify("View cannot be GCed");
	}

	/**
	 * A simple test to verify that the ViewModel instance can be
	 * garbage collected.
	 */
	@Test
	public void testViewModelCommunication() {

		ViewModel vm = new ViewModel() {};

		AtomicInteger counter = new AtomicInteger();

		vm.subscribe("test", (k, v) -> counter.incrementAndGet());

		GCVerifier.forceGC();


		vm.publish("test");


		waitForUiThread();


		assertThat(counter.get()).isEqualTo(1);


		GCVerifier verifier = GCVerifier.create(vm);


		vm = null;

		verifier.verify("VM cannot be GCed");
	}


	/**
	 * By using a new instance of a (static) class as observer,
	 * there is no leak possible by the observer.
	 *
	 * This test ensures that there is no leak from using the ViewModel instance
	 * as channel object.
	 */
	@Test
	public void testStaticObserverDoesntCreateMemoryLeak() {

		ViewModel vm = new ViewModel() {};

		StaticObserver.counter.set(0);


		vm.subscribe("test", new StaticObserver());

		GCVerifier.forceGC();

		vm.publish("test");

		waitForUiThread();

		assertThat(StaticObserver.counter.get()).isEqualTo(1);


		GCVerifier verifier = GCVerifier.create(vm);


		vm = null;

		verifier.verify("VM cannot be GCed");
	}

	private static class StaticObserver implements NotificationObserver {

		static AtomicInteger counter = new AtomicInteger();


		@Override
		public void receivedNotification(String key, Object... payload) {
			StaticObserver.counter.incrementAndGet();
		}
	}


	/**
	 * This method is used to wait until the UI thread has done all work that was queued via
	 * {@link Platform#runLater(Runnable)}.
	 */
	private void waitForUiThread() {
		CompletableFuture<Void> future = new CompletableFuture<>();
		Platform.runLater(() -> future.complete(null));
		try {
			future.get(1l, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new IllegalStateException(e);
		}
	}

}
