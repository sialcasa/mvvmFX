package de.saxsys.jfx.exampleapplication.view.personlogin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import de.saxsys.jfx.exampleapplication.viewmodel.personlogin.PersonLoginViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.commands.Command;

/**
 * Code behind the fxml for visualization of the {@link PersonLoginView}. The view binds to the properties of the
 * {@link PersonLoginViewModel}.
 *
 * @author alexander.casall
 */
public class PersonLoginView implements FxmlView<PersonLoginViewModel>, Initializable {
	
	@FXML
	// Injection of the person choiceBox which is declared in the FXML File
	private ChoiceBox<String> personsChoiceBox;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private ProgressIndicator loginProgressIndicator;
	
	@InjectViewModel
	private PersonLoginViewModel viewModel;
	
	private Command loginCommand;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		loginCommand = getViewModel().getLoginCommand();
		initChoiceBox();
		loginButton.disableProperty()
				.bind(loginCommand.executeableProperty().not().or(loginCommand.runningProperty()));
		loginProgressIndicator.visibleProperty().bind(loginCommand.runningProperty());
	}
	
	@FXML
	void loginButtonPressed(final ActionEvent event) {
		loginCommand.execute();
	}
	
	private void initChoiceBox() {
		personsChoiceBox.itemsProperty().bind(viewModel.selectablePersonsProperty().stringListProperty());
		
		personsChoiceBox
				.getSelectionModel()
				.selectedIndexProperty()
				.addListener(
						(ChangeListener<Number>) (arg0, oldVal, newVal) -> viewModel.selectablePersonsProperty()
								.select(newVal.intValue()));
	}
	
	public PersonLoginViewModel getViewModel() {
		return viewModel;
	}
}
