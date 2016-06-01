package de.saxsys.mvvmfx.testingutils;

import javafx.application.Platform;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FxTestingUtils {


	public static void waitForUiThread(long timeout) {
		CompletableFuture<Void> future = new CompletableFuture<>();

		Platform.runLater(() -> {
			future.complete(null);
		});

		try {
			future.get(timeout+50, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	public static void waitForUiThread() {
		waitForUiThread(0);
	}
}
