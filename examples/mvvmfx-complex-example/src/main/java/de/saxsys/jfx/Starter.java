package de.saxsys.jfx;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Injector;
import com.google.inject.Module;
import de.saxsys.jfx.exampleapplication.ExampleModule;
import de.saxsys.jfx.exampleapplication.view.maincontainer.MainContainerView;
import de.saxsys.jfx.exampleapplication.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.jfx.mvvm.api.MvvmFX;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.inject.Inject;
import java.util.List;

/**
 * Entry point of the application.
 * 
 * @author sialcasa
 * 
 */
public class Starter extends GuiceApplication {

	// Get the MVVM View Loader
	@Inject
	private ViewLoader viewLoader;

    @Inject
    private Injector injector;

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {

        // We need to tell mvvmFX how our Dependency-Injection works.
        MvvmFX.getDependencyInjector().setCustomInjector(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> type) {
                return injector.getInstance(type);
            }
        });



		final ViewTuple<MainContainerViewModel> tuple = viewLoader
				.loadViewTuple(MainContainerView.class);
		// Locate View for loaded FXML file
		final Parent view = tuple.getView();

		final Scene scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
	}

    @Override
    public void init(List<Module> modules) throws Exception {
        modules.add(new ExampleModule());
    }
}
