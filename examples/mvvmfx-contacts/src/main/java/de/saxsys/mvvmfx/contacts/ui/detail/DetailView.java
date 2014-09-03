package de.saxsys.mvvmfx.contacts.ui.detail;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
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
		nameLabel.visibleProperty().bind(nameLabel.managedProperty());
		
		birthdayLabel.textProperty().bind(viewModel.birthdayLabelTextProperty());
		birthdayLabel.managedProperty().bind(viewModel.birthdayLabelTextProperty().isNotEmpty());
		birthdayLabel.visibleProperty().bind(birthdayLabel.managedProperty());
		
		roleDepartmentLabel.textProperty().bind(viewModel.roleDepartmentLabelTextProperty());
		roleDepartmentLabel.managedProperty().bind(viewModel.roleDepartmentLabelTextProperty().isNotEmpty());
		roleDepartmentLabel.visibleProperty().bind(roleDepartmentLabel.managedProperty());
		
		emailHyperlink.textProperty().bind(viewModel.emailLabelTextProperty());
		emailHyperlink.setOnAction(event -> viewModel.onEmailLinkClicked());
		emailHyperlink.managedProperty().bind(viewModel.emailLabelTextProperty().isNotEmpty());
		emailHyperlink.visibleProperty().bind(emailHyperlink.managedProperty());
		
		phoneLabel.textProperty().bind(viewModel.phoneLabelTextProperty());
		phoneLabel.managedProperty().bind(viewModel.phoneLabelTextProperty().isNotEmpty());
		phoneLabel.visibleProperty().bind(phoneLabel.managedProperty());
		
		mobileLabel.textProperty().bind(viewModel.mobileLabelTextProperty());
		mobileLabel.managedProperty().bind(viewModel.mobileLabelTextProperty().isNotEmpty());
		mobileLabel.visibleProperty().bind(mobileLabel.managedProperty());
		
		
		// the email hyperlink should always look "unvisited".
		emailHyperlink.visitedProperty().bind(new SimpleBooleanProperty(false));
		
		initIcons();
	}
	
	private void initIcons(){
		AwesomeDude.setIcon(birthdayLabel, AwesomeIcon.BIRTHDAY_CAKE);
		AwesomeDude.setIcon(roleDepartmentLabel, AwesomeIcon.USERS);
		AwesomeDude.setIcon(emailHyperlink, AwesomeIcon.AT);
		AwesomeDude.setIcon(mobileLabel, AwesomeIcon.MOBILE_PHONE);
		AwesomeDude.setIcon(phoneLabel, AwesomeIcon.PHONE);
	}
	
}
