package de.saxsys.jfx.mvvmfx.helloworld;

import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Starter extends Application {


    public static void main(String...args){
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello World Application");

        ViewLoader viewLoader = new ViewLoader();

        ViewTuple<HelloWorldView, HelloWorldViewModel> viewTuple = viewLoader
                .loadViewTuple(HelloWorldView.class);

        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
