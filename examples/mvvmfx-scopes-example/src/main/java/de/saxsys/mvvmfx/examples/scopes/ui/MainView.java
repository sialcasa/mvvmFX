package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class MainView implements FxmlView {

	@FXML
	public VBox root;

    public void newNote(){
        final Parent view = FluentViewLoader.fxmlView(NoteView.class).load().getView();
        root.getChildren().add(view);
    }

}
