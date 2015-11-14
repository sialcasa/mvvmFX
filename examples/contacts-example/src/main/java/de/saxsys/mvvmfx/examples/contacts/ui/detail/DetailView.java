package de.saxsys.mvvmfx.examples.contacts.ui.detail;

import javax.inject.Inject;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.examples.contacts.ui.editcontact.EditContactDialog;
import de.saxsys.mvvmfx.examples.contacts.ui.editcontact.EditContactDialogViewModel;
import de.saxsys.mvvmfx.examples.contacts.util.DialogHelper;
import de.saxsys.mvvmfx.utils.commands.Command;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;

public class DetailView implements FxmlView<DetailViewModel> {
	@FXML
	public Label nameLabel, birthdayLabel, roleDepartmentLabel, phoneLabel, mobileLabel, cityPostalCodeLabel,
			streetLabel, countrySubdivisionLabel;
	@FXML
	public Hyperlink emailHyperlink;
	
	@FXML
	public Button editButton, removeButton;
	
	@Inject
	private Stage primaryStage;
	
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
		
		viewModel.subscribe(DetailViewModel.OPEN_EDIT_CONTACT_DIALOG, (key, payload) -> {
			ViewTuple<EditContactDialog, EditContactDialogViewModel> load = FluentViewLoader
					.fxmlView(EditContactDialog.class)
					.load();
			Parent view = load.getView();
			Stage showDialog = DialogHelper.showDialog(view, primaryStage, "/contacts.css");
			load.getCodeBehind().setOwningStage(showDialog);
		});
		
		
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
