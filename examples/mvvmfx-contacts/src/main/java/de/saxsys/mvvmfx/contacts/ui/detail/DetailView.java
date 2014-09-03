package de.saxsys.mvvmfx.contacts.ui.detail;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class DetailView implements FxmlView<DetailViewModel> {
	@FXML
	public Label nameLabel;
	@FXML
	public Label birthdayLabel;
	@FXML
	public Label roleDepartmentLabel;
	@FXML
	public Hyperlink emailHyperlink;
	@FXML
	public Label phoneLabel;
	@FXML
	public Label mobileLabel;
	
	
	@InjectViewModel
	private DetailViewModel viewModel;
	
	public void initialize() {
		nameLabel.textProperty().bind(viewModel.nameLabelTextProperty());
		nameLabel.managedProperty().bind(viewModel.nameLabelTextProperty().isNotEmpty());
		
		birthdayLabel.textProperty().bind(viewModel.birthdayLabelTextProperty());
		birthdayLabel.managedProperty().bind(viewModel.birthdayLabelTextProperty().isNotEmpty());
		
		roleDepartmentLabel.textProperty().bind(viewModel.roleDepartmentLabelTextProperty());
		roleDepartmentLabel.managedProperty().bind(viewModel.roleDepartmentLabelTextProperty().isNotEmpty());
		
		emailHyperlink.textProperty().bind(viewModel.emailLabelTextProperty());
		emailHyperlink.setOnAction(event -> viewModel.onEmailLinkClicked());
		emailHyperlink.managedProperty().bind(viewModel.emailLabelTextProperty().isNotEmpty());
		
		phoneLabel.textProperty().bind(viewModel.phoneLabelTextProperty());
		phoneLabel.managedProperty().bind(viewModel.phoneLabelTextProperty().isNotEmpty());
		
		mobileLabel.textProperty().bind(viewModel.mobileLabelTextProperty());
		mobileLabel.managedProperty().bind(viewModel.mobileLabelTextProperty().isNotEmpty());
		
		
		// the email hyperlink should always look "unvisited".
		emailHyperlink.visitedProperty().bind(new SimpleBooleanProperty(false));
	}
	
}
