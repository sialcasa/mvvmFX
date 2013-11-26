package de.saxsys.jfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(final String...args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new Pane(), 300, 300);
        stage.setScene(scene);
        stage.show();
    }
}
