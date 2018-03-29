package de.saxsys.mvvmfx.testingutils;

import javafx.application.Platform;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FxTestingUtils {


	public static void runInFXThread(Runnable code){
		runInFXThread(code, 1000);
	}

	public static void runInFXThread(Runnable code, long timeout){
		CompletableFuture<Void> future = new CompletableFuture<>();

		Platform.runLater(() -> {
			code.run();
			future.complete(null);
		});

		try {
			future.get(timeout+50, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to wait until the UI thread has done all work that was queued via
	 * {@link Platform#runLater(Runnable)}.
	 */
	public static void waitForUiThread(long timeout) {
		runInFXThread(() -> {}, timeout);
	}

	/**
	 * This method is used to wait until the UI thread has done all work that was queued via
	 * {@link Platform#runLater(Runnable)}.
	 */
	public static void waitForUiThread() {
		waitForUiThread(1000);
	}

	/**
	 * This method is used to put the platform thread on hold. In conjunction with waitForUiThread(), it
	 * allows to test that tasks are being queued on the thread.
	 *
	 * For example:
	 * <pre>
	 * {@code
	 * cut.usePlatformRunLaterToDoSomething(...);
	 * Semaphore semaphore = new Semaphore(0);
	 * FxTestingUtils.putPlatformThreadOnHold(semaphore);
	 * Mockito.verifyZeroInteractions(testDummy); // confirms runLater is being used
	 * semaphore.release();
	 * FxTestingUtils.waitForUiThread();
	 * Mockito.verify(testDummy).doSomething();
	 * }
	 * </pre>
	 *

	 * @param semaphore
	 * @throws InterruptedException
	 */
	public static void putPlatformThreadOnHold(Semaphore semaphore) throws InterruptedException {
		Platform.runLater(() -> {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
