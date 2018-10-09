package de.saxsys.mvvmfx.examples.jigsaw.app;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.examples.jigsaw.circle.CircleView;
import de.saxsys.mvvmfx.examples.jigsaw.circle.CircleViewModel;
import de.saxsys.mvvmfx.examples.jigsaw.rectangle.RectangleView;
import de.saxsys.mvvmfx.examples.jigsaw.rectangle.RectangleViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class AppView implements FxmlView<AppViewModel> {

    @FXML
    VBox appMainBox;
    @FXML
    VBox circle;

    @InjectViewModel
    private AppViewModel appViewModel;

    public void initialize() {

        //loadCircleView();
        loadRectangleView();
    }

    private void loadCircleView() {
        ResourceBundle circleBundle = ResourceBundle.getBundle("circle");

        ViewTuple<CircleView, CircleViewModel> viewTuple = FluentViewLoader.fxmlView(
                CircleView.class).resourceBundle(circleBundle).load();

        appMainBox.getChildren().add(viewTuple.getView());
    }

    private void loadRectangleView() {
        ResourceBundle rectangleBundle = ResourceBundle.getBundle("rectangle");

        ViewTuple<RectangleView, RectangleViewModel> viewTuple = FluentViewLoader.fxmlView(
                RectangleView.class).resourceBundle(rectangleBundle).load();

        appMainBox.getChildren().add(viewTuple.getView());
    }
}
