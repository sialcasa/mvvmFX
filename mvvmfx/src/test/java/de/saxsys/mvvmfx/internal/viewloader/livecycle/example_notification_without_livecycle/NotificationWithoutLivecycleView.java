package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_notification_without_livecycle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class NotificationWithoutLivecycleView implements FxmlView<NotificationWithoutLivecycleViewModel> {

	@InjectViewModel
	private NotificationWithoutLivecycleViewModel viewModel;
}
