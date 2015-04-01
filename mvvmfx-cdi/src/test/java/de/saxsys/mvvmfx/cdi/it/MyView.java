package de.saxsys.mvvmfx.cdi.it;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

import javax.inject.Inject;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;

public class MyView implements FxmlView<MyViewModel> {
	@Inject
	Stage primaryStage;
	
	@Inject
	Application.Parameters parameters;
	
	@Inject
	NotificationCenter notificationCenter;
	
	@Inject
	HostServices hostServices;
	
	public static int instanceCounter = 0;
	
	public MyView() {
		instanceCounter++;
	}
}
