package ${package};

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import de.saxsys.mvvmfx.ViewTuple;


public class Starter extends Application {
	
	
	public static void main(String... args) {
		Application.launch(args);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Hello World Application");

		ViewTuple<HelloWorldView, HelloWorldViewModel> viewTuple = FluentViewLoader.fxmlView(HelloWorldView.class).load();
		
		Parent root = viewTuple.getView();
		stage.setScene(new Scene(root));
		stage.show();
	}
}
