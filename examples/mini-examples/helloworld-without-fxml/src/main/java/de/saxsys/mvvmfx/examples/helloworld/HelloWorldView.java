package de.saxsys.mvvmfx.examples.helloworld;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;

public class HelloWorldView extends VBox implements JavaView<HelloWorldViewModel>, Initializable {

    private final Label helloLabel = new Label();

    @InjectViewModel
    private HelloWorldViewModel viewModel;

    public HelloWorldView() {
        getChildren().add(helloLabel);
        setPadding(new Insets(10, 10, 10, 10));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.textProperty().bind(viewModel.helloMessage());
    }
    
}
