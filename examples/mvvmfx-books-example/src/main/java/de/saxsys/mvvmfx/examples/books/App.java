package de.saxsys.mvvmfx.examples.books;

import com.guigarage.flatterfx.FlatterFX;
import com.guigarage.flatterfx.FlatterInputType;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.examples.books.backend.LibraryService;
import de.saxsys.mvvmfx.examples.books.backend.LibraryServiceImpl;
import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        EasyDI context = new EasyDI();
        context.bindInterface(LibraryService.class, LibraryServiceImpl.class);
        MvvmFX.setCustomDependencyInjector(context::getInstance);

        primaryStage.setTitle("Library JavaFX");
        primaryStage.setMinWidth(1200);
        primaryStage.setMaxWidth(1200);
        primaryStage.setMinHeight(700);

        Scene scene = new Scene(FluentViewLoader.fxmlView(MainView.class).load().getView(), 1200, 700);

        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        FlatterFX.style(FlatterInputType.DEFAULT);
    }
}

