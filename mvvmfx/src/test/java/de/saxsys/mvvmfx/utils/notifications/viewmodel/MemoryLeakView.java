package de.saxsys.mvvmfx.utils.notifications.viewmodel;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;
import de.saxsys.mvvmfx.utils.notifications.WeakNotificationObserver;

import java.util.concurrent.atomic.AtomicInteger;

public class MemoryLeakView implements FxmlView<MemoryLeakViewModel> {


	public MemoryLeakViewModel viewModel;

	public AtomicInteger counter = new AtomicInteger();

	private NotificationObserver observer = (k, v) -> {
		counter.incrementAndGet();
	};

	public void init() {
		viewModel.subscribe(MemoryLeakViewModel.MESSAGE_NAME, new WeakNotificationObserver(observer));
	}
}
