package de.saxsys.jfx.mvvmfx.fx_root_example;

import de.saxsys.jfx.mvvm.viewloader.FluentViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;

public class LabeledTextField extends HBox implements FxmlView<LabeledTextFieldViewModel> {
	
	@FXML
	private Label label;
	
	@FXML
	private TextField textField;
	
	@FXML
	private Button button;
	
	@InjectViewModel
	private LabeledTextFieldViewModel viewModel;
	
	public LabeledTextField() {
		FluentViewLoader.fxmlView(this.getClass()).root(this).codeBehind(this).load();
	}
	
	public void initialize() {
		label.textProperty().bind(viewModel.labelTextProperty());
		textField.textProperty().bindBidirectional(viewModel.inputTextProperty());
		
		button.disableProperty().bind(viewModel.buttonDisabledProperty());
	}
	
	@FXML
	public void onAction() {
		viewModel.changeLabel();
	}
	
	
}
