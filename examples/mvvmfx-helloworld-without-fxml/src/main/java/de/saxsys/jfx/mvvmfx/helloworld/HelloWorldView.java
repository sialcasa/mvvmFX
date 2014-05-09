package de.saxsys.jfx.mvvmfx.helloworld;

import de.saxsys.jfx.mvvm.api.InjectViewModel;
import de.saxsys.jfx.mvvm.api.JavaView;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloWorldView extends VBox implements JavaView<HelloWorldViewModel> , Initializable{

    private Label helloLabel = new Label();
    
    @InjectViewModel
    private HelloWorldViewModel viewModel;

    public HelloWorldView() {
        getChildren().add(helloLabel);
        setPadding(new Insets(10,10,10,10));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        helloLabel.textProperty().bind(viewModel.helloMessage());
    }
}
