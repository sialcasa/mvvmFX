package de.saxsys.mvvmfx.contacts;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import de.saxsys.mvvmfx.contacts.ui.main.MainView;
import de.saxsys.mvvmfx.contacts.ui.main.MainViewModel;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends MvvmfxCdiApplication{
	
	
	public static void main(String...args){
		launch(args);
	}
	
	
	@Override 
	public void start(Stage stage) throws Exception {

		ViewTuple<MainView, MainViewModel> main = FluentViewLoader.fxmlView(MainView.class).load();
		
		stage.setScene(new Scene(main.getView()));
		stage.show();
	}
}
