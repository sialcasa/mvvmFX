package de.saxsys.mvvmfx.examples.contacts.ui.detail;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.scene.control.Labeled;

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
	
	@FXML
	public Label cityPostalCodeLabel;
	@FXML
	public Label streetLabel;
	@FXML
	public Label countrySubdivisionLabel;
	
	
	@FXML
	public Button editButton;
	@FXML
	public Button removeButton;
	
	
	@InjectViewModel
	private DetailViewModel viewModel;
	
	public void initialize() {
		removeButton.disableProperty().bind(viewModel.removeButtonDisabledProperty());
		editButton.disableProperty().bind(viewModel.editButtonDisabledProperty());
		
		
		nameLabel.textProperty().bind(viewModel.nameLabelTextProperty());
		initVisibilityBindings(nameLabel);
		
		birthdayLabel.textProperty().bind(viewModel.birthdayLabelTextProperty());
		initVisibilityBindings(birthdayLabel);
		
		roleDepartmentLabel.textProperty().bind(viewModel.roleDepartmentLabelTextProperty());
		initVisibilityBindings(roleDepartmentLabel);
		
		emailHyperlink.textProperty().bind(viewModel.emailLabelTextProperty());
		emailHyperlink.setOnAction(event -> viewModel.onEmailLinkClicked());
		initVisibilityBindings(emailHyperlink);
		
		phoneLabel.textProperty().bind(viewModel.phoneLabelTextProperty());
		initVisibilityBindings(phoneLabel);
		
		mobileLabel.textProperty().bind(viewModel.mobileLabelTextProperty());
		initVisibilityBindings(mobileLabel);
		
		
		// the email hyperlink should always look "unvisited".
		emailHyperlink.visitedProperty().bind(new SimpleBooleanProperty(false));
		
		
		cityPostalCodeLabel.textProperty().bind(viewModel.cityPostalcodeLabelTextProperty());
		initVisibilityBindings(cityPostalCodeLabel);
		
		streetLabel.textProperty().bind(viewModel.streetLabelTextProperty());
		initVisibilityBindings(streetLabel);
		
		countrySubdivisionLabel.textProperty().bind(viewModel.countrySubdivisionLabelTextProperty());
		initVisibilityBindings(countrySubdivisionLabel);
		
		initIcons();
	}
	
	private void initVisibilityBindings(Labeled label) {
		label.visibleProperty().bind(label.textProperty().isNotEmpty());
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
	public void edit() {
		viewModel.editAction();
	}
	
	@FXML
	public void remove() {
		viewModel.removeAction();
	}
}
