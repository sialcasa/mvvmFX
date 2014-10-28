package de.saxsys.mvvmfx.guice.it;

import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

import javax.inject.Inject;

import de.saxsys.mvvmfx.FxmlView;

public class MyView implements FxmlView<MyViewModel> {
	@Inject
	Stage primaryStage;

	@Inject
	Application.Parameters parameters;

	@Inject
	NotificationCenter notificationCenter;

	@Inject
	HostServices hostServices;
	
}
