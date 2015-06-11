package de.saxsys.mvvmfx.utils.notifications;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author manuel.mauky
 */
public class NotificationTestHelper implements NotificationObserver {
	
	private List<Pair<String, Object[]>> notifications = new ArrayList<>();
	
	public NotificationTestHelper() {
		new JFXPanel();
	}
	
	@Override
	public void receivedNotification(String key, Object... payload) {
		notifications.add(new Pair<>(key, payload));
	}
	
	public int numberOfCalls() {
		waitForUiThread();
		return notifications.size();
	}
	
	public int numberOfCalls(String key) {
		waitForUiThread();
		return (int) notifications.stream()
				.filter(pair -> pair.getKey().equals(key))
				.count();
	}
	
	private void waitForUiThread() {
		CompletableFuture<Void> future = new CompletableFuture<>();

		Platform.runLater(() -> future.complete(null));

		try {
			future.get(1l, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
