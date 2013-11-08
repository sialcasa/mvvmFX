package de.saxsys.jfx;

import javafx.stage.Stage;
import de.saxsys.jfx.mvvm.di.cdi.MvvmCdiApplication;

/**
 * The application entry point. The JavaFX specific code 
 * with access to the primary {@link Stage} is located in {@link App}.
 * 
 * @author manuel.mauky
 *
 */
public class Starter extends MvvmCdiApplication{
	
	public static void main(String...args){
		launch(args);
	}
}
