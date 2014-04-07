package de.saxsys.jfx.mvvmfx.helloworld;

import de.saxsys.jfx.mvvm.api.JavaView;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelloWorldView extends VBox implements JavaView<HelloWorldViewModel> {

    private Label helloLabel = new Label();

    public HelloWorldView() {
        getChildren().add(helloLabel);
        setPadding(new Insets(10,10,10,10));
    }

    @Override
    public void setViewModel(HelloWorldViewModel viewModel) {
        helloLabel.textProperty().bind(viewModel.helloMessage());
    }

}
