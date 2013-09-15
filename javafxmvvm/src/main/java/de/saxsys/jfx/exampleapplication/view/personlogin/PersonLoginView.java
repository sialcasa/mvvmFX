package de.saxsys.jfx.exampleapplication.view.personlogin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import de.saxsys.jfx.exampleapplication.view.personwelcome.PersonWelcomeView;
import de.saxsys.jfx.exampleapplication.viewmodel.personlogin.PersonLoginViewModel;
import de.saxsys.jfx.exampleapplication.viewmodel.personwelcome.PersonWelcomeViewModel;
import de.saxsys.jfx.mvvm.base.MVVMView;
import de.saxsys.jfx.mvvm.viewloader.MVVMViewLoader;
import de.saxsys.jfx.mvvm.viewloader.MVVMViewNames;
import de.saxsys.jfx.mvvm.viewloader.MVVMViewTuple;

/**
 * Code behind the fxml for visualization of the {@link PersonLoginView}. After
 * the {@link PersonLoginViewModel} is set, the view binds to the
 * {@link PersonLoginViewModel}.
 * 
 * @author alexander.casall
 */
public class PersonLoginView extends MVVMView<PersonLoginViewModel> {

	@FXML
	private ChoiceBox<String> personsChoiceBox;

	@FXML
	private VBox layoutVbox;

	private Parent personWelcome;

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
	}

	@Override
	public void beforeViewModelInitialization() {
	}

	@Override
	public void afterViewModelInitialization() {
		personsChoiceBox.itemsProperty().bind(viewModel.personsProperty());
	}

	@FXML
	void loginButtonPressed(final ActionEvent event) {
		// Remove if already exists
		layoutVbox.getChildren().remove(personWelcome);

		final MVVMViewTuple tuple = new MVVMViewLoader()
				.loadViewTuple(MVVMViewNames.PERSONWELCOME.getResource());

		// Locate code-behind with view
		final PersonWelcomeView personWelcomeView = (PersonWelcomeView) tuple
				.getCodeBehind();

		personWelcome = tuple.getView();

		// Create ViewModel
		final int personId = personsChoiceBox.getSelectionModel()
				.getSelectedIndex();
		final PersonWelcomeViewModel personWelcomeViewPm = new PersonWelcomeViewModel(
				personId);

		personWelcomeView.setViewModel(personWelcomeViewPm);

		layoutVbox.getChildren().add(personWelcome);
	}

}
