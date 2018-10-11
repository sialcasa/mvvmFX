package de.saxsys.mvvmfx.examples.jigsaw.app;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String... args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage){

        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");

        stage.setTitle(resourceBundle.getString("app.stage.title"));

        ViewTuple<AppView, AppViewModel> viewTuple = FluentViewLoader.fxmlView(AppView.class).load();
        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
