package de.saxsys.mvvmfx.examples.scopes;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import de.saxsys.mvvmfx.examples.scopes.model.NoteStore;
import de.saxsys.mvvmfx.examples.scopes.ui.MainView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String... args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		NoteStore.getInstance().put(new Note("test 1", "Some text 1"));
		NoteStore.getInstance().put(new Note("test 2", "Some text 2"));
		NoteStore.getInstance().put(new Note("test 3", "Some text 3"));
		NoteStore.getInstance().put(new Note("test 4", "Some text 4"));

		Parent mainView = FluentViewLoader.fxmlView(MainView.class).load().getView();
		
		stage.setScene(new Scene(mainView));
		stage.show();
	}
}
