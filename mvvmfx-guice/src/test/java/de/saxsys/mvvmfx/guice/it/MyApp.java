package de.saxsys.mvvmfx.guice.it;

import javafx.application.Platform;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;


public class MyApp extends MvvmfxGuiceApplication {
	
	
	static ViewTuple<MyView, MyViewModel> viewTuple;
	
	static Stage stage;
	
	@Override
	public void startMvvmfx(Stage stage) throws Exception {
		MyApp.stage = stage;
		MyApp.viewTuple = FluentViewLoader.fxmlView(MyView.class).load();
		Platform.exit();
	}
}
