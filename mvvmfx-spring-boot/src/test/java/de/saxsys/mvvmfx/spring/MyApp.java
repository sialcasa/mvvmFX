package de.saxsys.mvvmfx.spring;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication
public class MyApp extends MvvmfxSpringApplication {

	static boolean wasPreDestroyCalled = false;
	static boolean wasPostConstructCalled = false;

	static boolean wasInitCalled = false;
	static boolean wasStopCalled = false;


	static ViewTuple<MyView, MyViewModel> viewTuple;

	static Stage stage;

	public static void main(String[] args) {
		String[] newArgs = new String[] { "test" };
		Application.launch(newArgs);
	}

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
