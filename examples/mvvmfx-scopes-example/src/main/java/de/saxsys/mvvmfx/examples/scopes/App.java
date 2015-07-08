package de.saxsys.mvvmfx.examples.scopes;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import de.saxsys.mvvmfx.examples.scopes.ui.MainView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends MvvmfxCdiApplication{

	public static void main(String... args) {
		launch(args);
	}
	
    @Override
    public void startMvvmfx(Stage stage) throws Exception {
        Parent mainView = FluentViewLoader.fxmlView(MainView.class).load().getView();

        stage.setScene(new Scene(mainView));
        stage.show();
    }
}
