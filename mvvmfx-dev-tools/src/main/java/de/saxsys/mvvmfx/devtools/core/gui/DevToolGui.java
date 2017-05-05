package de.saxsys.mvvmfx.devtools.core.gui;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNode;
import de.saxsys.mvvmfx.devtools.core.gui.main.MainView;
import de.saxsys.mvvmfx.devtools.core.gui.main.MainViewModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class DevToolGui {


    private List<FxmlNode> fxmlRootNodes;

    public DevToolGui(List<FxmlNode> fxmlRootNodes) {
        this.fxmlRootNodes = fxmlRootNodes;
    }


    public void start(Stage stage) {
        stage.setTitle("Hello World Application");

        FxmlNodesScope scope = new FxmlNodesScope();
        scope.getFxmlNodes().setAll(fxmlRootNodes);

        final ViewTuple<MainView, MainViewModel> viewTuple = FluentViewLoader.fxmlView(MainView.class).providedScopes(scope).load();

        final Parent root = viewTuple.getView();
        stage.setScene(new Scene(root, 1000, 800));
        stage.show();
    }

}
