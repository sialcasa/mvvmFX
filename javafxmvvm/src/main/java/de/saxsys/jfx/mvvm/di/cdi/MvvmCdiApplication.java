package de.saxsys.jfx.mvvm.di.cdi;


import javax.enterprise.event.Observes;

import javafx.application.Application;
import javafx.stage.Stage;

import org.jboss.weld.environment.se.Weld;


/**
 * Base Application class that needs to be extended to use CDI as Dependency
 * Injection framework.
 * 
 * When the JavaFX {@link Stage} is ready an {@link StartupEvent} is fired that will contain the
 * {@link Stage}. You should implement an CDI observer (with {@link Observes}) that listens for this event
 * to get the stage and build your UI.
 * 
 * @author manuel.mauky
 * 
 */
public class MvvmCdiApplication extends Application {
	
	private Weld weld;
	
	@Override
	public final void init() throws Exception {
		super.init();
		weld = new Weld();
	}
	
	
	@Override
	public final void start(Stage primaryStage) throws Exception {
		weld.initialize().event().fire(new StartupEvent(primaryStage,getParameters()));
	}
	
	@Override
	public final void stop() throws Exception {
		weld.shutdown();
		super.stop();
	}
	
	
	
}
