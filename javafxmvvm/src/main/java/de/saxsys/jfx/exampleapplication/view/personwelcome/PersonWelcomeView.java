package de.saxsys.jfx.exampleapplication.view.personwelcome;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import de.saxsys.jfx.exampleapplication.viewmodel.personwelcome.PersonWelcomeViewModel;
import de.saxsys.jfx.mvvm.base.MVVMView;

/**
 * Code behind the fxml for visualization of the PersonWelcomeViewModel. After the {@link PersonWelcomeViewModel} is
 * set, the view binds to the {@link PersonWelcomeViewModel}.
 * 
 * @author alexander.casall
 */
public class PersonWelcomeView extends MVVMView<PersonWelcomeViewModel> {

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {
    }

    @Override
    public void beforeViewModelInitialization() {

    }

    @Override
    public void afterViewModelInitialization() {
        welcomeLabel.textProperty().bind(viewModel.welcomeStringProperty());
    }
}
