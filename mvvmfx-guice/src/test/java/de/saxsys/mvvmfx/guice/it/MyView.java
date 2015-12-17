package de.saxsys.mvvmfx.guice.it;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

import javax.inject.Inject;

public class MyView implements FxmlView<MyViewModel> {
	@Inject
	Stage primaryStage;
	
	@Inject
	Application.Parameters parameters;
	
	@Inject
	NotificationCenter notificationCenter;
	
	@Inject
	HostServices hostServices;

    public static boolean wasInitCalled = false;


    public void initialize() {
        wasInitCalled = true;
    }
}
