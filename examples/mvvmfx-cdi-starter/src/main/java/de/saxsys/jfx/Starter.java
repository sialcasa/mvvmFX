package de.saxsys.jfx;

import de.saxsys.jfx.cdi.CdiInjector;
import de.saxsys.jfx.mvvm.api.MvvmFX;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * The application entry point. The JavaFX specific code 
 * with access to the primary {@link Stage} is located in {@link App}.
 * 
 * @author manuel.mauky
 *
 */
public class Starter extends Application{
	
	public static void main(String...args){
		launch(args);
	}

    @Override
    public void start(Stage stage) throws Exception {

        WeldContainer weldContainer = new Weld().initialize();


        CdiInjector cdiInjector = weldContainer.instance().select(CdiInjector.class).get();

        MvvmFX.getDependencyInjector().setCustomInjector(cdiInjector);


        weldContainer.instance().select(App.class).get().startApplication(stage);
    }
}
