package de.saxsys.mvvmfx.examples.helloworld.view;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class HelloView implements FxmlView<HelloViewModel> {

	@FXML
	private Label message;

	@FXML
	private TextField name;

	@InjectViewModel
	private HelloViewModel viewModel;

	public void initialize() {
		viewModel.nameProperty().bindBidirectional(name.textProperty());

		message.textProperty().bind(viewModel.messageProperty());
	}
}
