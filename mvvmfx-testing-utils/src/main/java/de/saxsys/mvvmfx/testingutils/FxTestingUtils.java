package de.saxsys.mvvmfx.testingutils;

import javafx.application.Platform;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FxTestingUtils {
	/**
	 * This method is used to execute some code on the JavaFX Thread.
	 * It is intended to be used in testing scenarios only because the internal exception
	 * handling is optimized for testing purposes.
	 *
	 * This method is blocking. It will execute the given code runnable and wait
	 * for it to be finished. However, a timeout of 1000 milliseconds is used.
	 * If you need another timeout value, use {@link #runInFXThread(Runnable, long)}.
	 *
	 * This method will catch exceptions produced by the execution of the code.
	 * If an {@link AssertionError} is thrown by the code (for example because an test assertion has failed)
	 * the assertion error will be rethrown by this method so that the JUnit test runner will properly handle it
	 * and show you the test failure.
	 * @param code the code to execute on the JavaFX Thread
	 */
	public static void runInFXThread(Runnable code){
		runInFXThread(code, 1000);
	}

	/**
	 * See {@link #runInFXThread(Runnable)}. This method takes a timeout of milliseconds
	 * as second parameter.
	 * @param code the code to execute on the JavaFX Thread
	 * @param timeout the timeout to be used before the execution is canceled.
	 */
	public static void runInFXThread(Runnable code, long timeout){
		CompletableFuture<Object> future = new CompletableFuture<>();

		Platform.runLater(() -> {
			try {
				code.run();
			} catch (AssertionError e) {
				future.complete(e);
				return;
			}
			future.complete(null);
		});

		try {
			Object result = future.get(timeout + 50, TimeUnit.MILLISECONDS);

			if(result instanceof AssertionError) {
				throw (AssertionError) result;
			}
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
}
