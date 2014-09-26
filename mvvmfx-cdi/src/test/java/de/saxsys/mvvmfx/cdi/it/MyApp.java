package de.saxsys.mvvmfx.cdi.it;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MyApp extends MvvmfxCdiApplication {
	
	
	static ViewTuple<MyView, MyViewModel> viewTuple;
	
	static Stage stage;
	
	@Override
	public void startMvvmfx(Stage stage) throws Exception {
		MyApp.stage = stage;
		MyApp.viewTuple = FluentViewLoader.fxmlView(MyView.class).load();
		Platform.exit();
	}
}
