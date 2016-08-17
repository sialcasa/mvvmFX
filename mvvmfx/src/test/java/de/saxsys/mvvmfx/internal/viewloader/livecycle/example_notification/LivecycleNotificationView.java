package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_notification;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LivecycleNotificationView implements FxmlView<LivecycleNotificationViewModel> {

	@InjectViewModel
	private LivecycleNotificationViewModel viewModel;
}
