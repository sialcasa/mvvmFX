package de.saxsys.mvvmfx.cdi.it;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MyApp extends MvvmfxCdiApplication {
	
	static boolean wasPreDestroyCalled = false;
	static boolean wasPostConstructCalled = false;
	
	static boolean wasInitCalled = false;
	static boolean wasStopCalled = false;
	
	
	
	static ViewTuple<MyView, MyViewModel> viewTuple;
	
	static Stage stage;
	
	@Override
	public void startMvvmfx(Stage stage) throws Exception {
		MyApp.stage = stage;
		MyApp.viewTuple = FluentViewLoader.fxmlView(MyView.class).load();
		Platform.exit();
	}
	
	@PostConstruct
	public void postConstruct() {
		wasPostConstructCalled = true;
	}
	
	@PreDestroy
	public void preDestroy() {
		wasPreDestroyCalled = true;
	}
	
	
	@Override
	public void initMvvmfx() throws Exception {
		wasInitCalled = true;
	}
	
	@Override
	public void stopMvvmfx() throws Exception {
		wasStopCalled = true;
	}
}
