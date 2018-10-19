package de.saxsys.mvvmfx.spring;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.application.HostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MyView implements FxmlView<MyViewModel> {

	@Autowired
	NotificationCenter notificationCenter;

	@Autowired
	HostServices hostServices;

	public static int instanceCounter = 0;

	public MyView() {
		instanceCounter++;
	}
}
