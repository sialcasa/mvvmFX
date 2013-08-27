package de.saxsys.jfx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.saxsys.jfx.exampleapplication.view.personlogin.PersonLoginView;
import de.saxsys.jfx.exampleapplication.viewmodel.personlogin.PersonLoginViewModel;
import de.saxsys.jfx.mvvm.MVVMTuple;
import de.saxsys.jfx.mvvm.MVVMViewLoader;
import de.saxsys.jfx.mvvm.MVVMViewNames;

public class Starter extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final MVVMTuple tuple = new MVVMViewLoader().getTuple(MVVMViewNames.PERSONLOGIN);

        // Locate code-behind with view
        final PersonLoginView personLoginView = (PersonLoginView) tuple.getCodeBehind();

        // Locate View for loaded FXML file
        final Parent view = tuple.getView();

        // Create ViewModel
        final PersonLoginViewModel personLoginViewModel = new PersonLoginViewModel();

        personLoginView.setViewModel(personLoginViewModel);

        final Scene scene = new Scene(view);

        stage.setScene(scene);
        stage.show();
    }

}
