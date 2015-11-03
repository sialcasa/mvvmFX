package de.saxsys.mvvmfx.examples.contacts.ui.detail;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

public class DetailView implements FxmlView<DetailViewModel> {
	@FXML
	public Label nameLabel, birthdayLabel, roleDepartmentLabel, phoneLabel, mobileLabel, cityPostalCodeLabel,
			streetLabel, countrySubdivisionLabel;
	@FXML
	public Hyperlink emailHyperlink;
	
	@FXML
	public Button editButton, removeButton;
	
	
	@InjectViewModel
	private DetailViewModel viewModel;
	
	private Command removeCommand;
	private Command editCommand;
	private Command mailCommand;
	
	public void initialize() {
		removeCommand = viewModel.getRemoveCommand();
		editCommand = viewModel.getEditCommand();
		mailCommand = viewModel.getEmailLinkCommand();
		
		removeButton.disableProperty().bind(removeCommand.notExecutableProperty());
		editButton.disableProperty().bind(editCommand.notExecutableProperty());
		
		nameLabel.setText("");
		nameLabel.textProperty().bind(viewModel.nameLabelTextProperty());
		
		nameLabel.textProperty().bind(viewModel.nameLabelTextProperty());
		birthdayLabel.textProperty().bind(viewModel.birthdayLabelTextProperty());
		roleDepartmentLabel.textProperty().bind(viewModel.roleDepartmentLabelTextProperty());
		emailHyperlink.textProperty().bind(viewModel.emailLabelTextProperty());
		phoneLabel.textProperty().bind(viewModel.phoneLabelTextProperty());
		mobileLabel.textProperty().bind(viewModel.mobileLabelTextProperty());
		cityPostalCodeLabel.textProperty().bind(viewModel.cityPostalcodeLabelTextProperty());
		streetLabel.textProperty().bind(viewModel.streetLabelTextProperty());
		countrySubdivisionLabel.textProperty().bind(viewModel.countrySubdivisionLabelTextProperty());
		
		initVisibilityBindings(nameLabel);
		initVisibilityBindings(birthdayLabel);
		initVisibilityBindings(roleDepartmentLabel);
		initVisibilityBindings(emailHyperlink);
		initVisibilityBindings(phoneLabel);
		initVisibilityBindings(mobileLabel);
		initVisibilityBindings(cityPostalCodeLabel);
		initVisibilityBindings(streetLabel);
		initVisibilityBindings(countrySubdivisionLabel);
		
		initIcons();
	}
	
	private void initVisibilityBindings(Labeled label) {
		label.visibleProperty().bind(editCommand.executableProperty());
		label.managedProperty().bind(label.visibleProperty());
	}
	
	private void initIcons() {
		AwesomeDude.setIcon(birthdayLabel, AwesomeIcon.BIRTHDAY_CAKE);
		AwesomeDude.setIcon(roleDepartmentLabel, AwesomeIcon.USERS);
		AwesomeDude.setIcon(emailHyperlink, AwesomeIcon.AT);
		AwesomeDude.setIcon(mobileLabel, AwesomeIcon.MOBILE_PHONE);
		AwesomeDude.setIcon(phoneLabel, AwesomeIcon.PHONE);
		AwesomeDude.setIcon(editButton, AwesomeIcon.EDIT);
		AwesomeDude.setIcon(removeButton, AwesomeIcon.TRASH_ALT);
	}
	
	@FXML
	public void editAction() {
		editCommand.execute();
	}
	
	@FXML
	public void removeAction() {
		removeCommand.execute();
	}
	
	@FXML
	public void mailAction() {
		mailCommand.execute();
	}
}
