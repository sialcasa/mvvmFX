package de.saxsys.jfx.mvvmfx.helloworld;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class HelloWorldView implements FxmlView<HelloWorldViewModel>, Initializable {
	
	@FXML
	private Label helloLabel;
	
	@InjectViewModel
	private HelloWorldViewModel viewModel;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		helloLabel.textProperty().bind(viewModel.helloMessage());
	}
}
