package de.saxsys.jfx.exampleapplication.view.personlogin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import de.saxsys.jfx.exampleapplication.viewmodel.personlogin.PersonLoginViewModel;
import de.saxsys.jfx.mvvm.base.view.View;

/**
 * Code behind the fxml for visualization of the {@link PersonLoginView}. The
 * view binds to the properties of the {@link PersonLoginViewModel}.
 * 
 * @author alexander.casall
 */
public class PersonLoginView extends View<PersonLoginViewModel> {

	@FXML
	// Injection of the person choiceBox which is declared in the FXML File
	private ChoiceBox<String> personsChoiceBox;

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
		personsChoiceBox.itemsProperty()
				.bind(getViewModel().selectablePersonsProperty()
						.stringListProperty());

		personsChoiceBox.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number oldVal, Number newVal) {
						getViewModel().selectablePersonsProperty().select(
								newVal.intValue());
					}
				});
	}

	@FXML
	void loginButtonPressed(final ActionEvent event) {
		getViewModel().login();
	}

}
