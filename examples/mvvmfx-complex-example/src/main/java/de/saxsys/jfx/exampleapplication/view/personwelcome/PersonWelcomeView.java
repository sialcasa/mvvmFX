package de.saxsys.jfx.exampleapplication.view.personwelcome;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.inject.Inject;

import de.saxsys.jfx.exampleapplication.viewmodel.personwelcome.PersonWelcomeViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;

/**
 * Code behind the fxml for visualization of the PersonWelcomeViewModel. The
 * view binds to the property of the {@link PersonWelcomeViewModel}.
 * 
 * @author alexander.casall
 */
public class PersonWelcomeView implements FxmlView<PersonWelcomeViewModel>, Initializable{

	@FXML
	// Injection of the label which is declared in the FXML File and shows the
	// welcome message
	private Label welcomeLabel;

	@Inject
	private NotificationCenter notificationCenter;
    
    @InjectViewModel
    private PersonWelcomeViewModel viewModel;

	@FXML
	// Handler for Button[Button[id=null, styleClass=button]] onAction
	public void closeApplicationButtonPressed(ActionEvent event) {
		// MainContainerView.java will handle it
		notificationCenter.postNotification("hidePersonWelcome", viewModel
                .getPersonId());
	}

    public PersonWelcomeViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.textProperty()
                .bind(viewModel.welcomeStringProperty());
    }
}
