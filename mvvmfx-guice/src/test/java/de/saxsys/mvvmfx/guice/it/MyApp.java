package de.saxsys.mvvmfx.guice.it;

import de.saxsys.mvvmfx.guice.internal.GuiceInjector;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;

import javax.inject.Inject;


public class MyApp extends MvvmfxGuiceApplication {


	static boolean wasInitCalled = false;
	static boolean wasStopCalled = false;
	
	public static void main(String...args){
		launch(args);
	}
	
	
	static ViewTuple<MyView, MyViewModel> viewTuple;
	
	/**
	 * To be able to verify that there was a valid stage available we need to persist the stage so we can verify it
	 * after the application has stopped. This needs to be static because we can't create an instance of this
	 * Application class on our own. This is done by the framework.
	 */
	static Stage stage;

	static Application.Parameters parameters;

	@Inject
	private GuiceInjector guiceInjector;

	//This is needed to be able to access the initialized guice injector from the outside
	static GuiceInjector staticInjector;
	
	@Override
	public void startMvvmfx(Stage stage) throws Exception {
		MyApp.staticInjector = guiceInjector;
		MyApp.stage = stage;
		MyApp.parameters = getParameters();
		
		MyApp.viewTuple = FluentViewLoader.fxmlView(MyView.class).load();
		
		// we can't shutdown the application in the test case so we need to do it here.
		Platform.exit();
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
