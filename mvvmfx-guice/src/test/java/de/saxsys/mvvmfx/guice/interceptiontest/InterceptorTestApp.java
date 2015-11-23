package de.saxsys.mvvmfx.guice.interceptiontest;

import com.google.inject.Module;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.List;

public class InterceptorTestApp extends MvvmfxGuiceApplication {

    public static ViewTuple<InterceptedView, InterceptedViewModel> viewTuple;


    public static void main(String... args) {
        launch(args);
    }


    @Override
    public void startMvvmfx(Stage stage) throws Exception {

        viewTuple = FluentViewLoader.fxmlView(InterceptedView.class).load();

        // we can't shutdown the application in the test case so we need to do it here.
        Platform.exit();
    }


    @Override
    public void initGuiceModules(List<Module> modules) throws Exception {
        modules.add(new InterceptorModule());
    }
}
