package usecases.nestedviewmodels;/**
 * @author manuel.mauky
 */

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final Parent parent = FluentViewLoader.fxmlView(MainView.class).load().getView();
		
		primaryStage.setScene(new Scene(parent));
		primaryStage.show();

	}
}
