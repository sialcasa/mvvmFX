package de.saxsys.mvvmfx.examples.helloworld;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.examples.helloworld.view.HelloView;
import de.saxsys.mvvmfx.spring.MvvmfxSpringApplication;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App extends MvvmfxSpringApplication {

	private static Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void startMvvmfx(Stage stage) throws Exception {
		Parent root = FluentViewLoader.fxmlView(HelloView.class).load().getView();

		stage.setScene(new Scene(root, 500, 300));
		stage.show();
	}
}
