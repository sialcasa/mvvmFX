package de.saxsys.jfx.exampleapplication.view.personlogin;

import de.saxsys.jfx.exampleapplication.viewmodel.personlogin.PersonLoginViewModel;
import de.saxsys.jfx.mvvm.api.FxmlView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Code behind the fxml for visualization of the {@link PersonLoginView}. The view binds to the properties of the {@link
 * PersonLoginViewModel}.
 *
 * @author alexander.casall
 */
public class PersonLoginView implements FxmlView<PersonLoginViewModel>, Initializable {

    @FXML
    // Injection of the person choiceBox which is declared in the FXML File
    private ChoiceBox<String> personsChoiceBox;


    private PersonLoginViewModel viewModel;

    @FXML
    void loginButtonPressed(final ActionEvent event) {
        viewModel.login();
    }

    @Override
    public void setViewModel(final PersonLoginViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public PersonLoginViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personsChoiceBox.itemsProperty()
                .bind(viewModel.selectablePersonsProperty()
                        .stringListProperty());

        personsChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> arg0,
                            Number oldVal, Number newVal) {
                        viewModel.selectablePersonsProperty().select(
                                newVal.intValue());
                    }
                });
    }
}
