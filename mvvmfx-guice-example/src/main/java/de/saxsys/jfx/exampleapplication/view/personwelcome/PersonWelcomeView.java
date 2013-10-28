package de.saxsys.jfx.exampleapplication.view.personwelcome;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import de.saxsys.jfx.exampleapplication.viewmodel.personwelcome.PersonWelcomeViewModel;
import de.saxsys.jfx.mvvm.base.view.View;

/**
 * Code behind the fxml for visualization of the PersonWelcomeViewModel. The
 * view binds to the property of the {@link PersonWelcomeViewModel}.
 * 
 * @author alexander.casall
 */
public class PersonWelcomeView extends View<PersonWelcomeViewModel> {

	@FXML
	// Injection of the label which is declared in the FXML File and shows the
	// welcome message
	private Label welcomeLabel;

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
		welcomeLabel.textProperty()
				.bind(getViewModel().welcomeStringProperty());
	}
}
