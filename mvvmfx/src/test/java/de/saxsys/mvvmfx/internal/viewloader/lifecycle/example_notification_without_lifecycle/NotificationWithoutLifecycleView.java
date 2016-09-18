package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_notification_without_lifecycle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class NotificationWithoutLifecycleView implements FxmlView<NotificationWithoutLifecycleViewModel> {

	@InjectViewModel
	private NotificationWithoutLifecycleViewModel viewModel;
}
