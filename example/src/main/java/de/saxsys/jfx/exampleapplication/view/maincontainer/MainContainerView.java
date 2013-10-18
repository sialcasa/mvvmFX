package de.saxsys.jfx.exampleapplication.view.maincontainer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import de.saxsys.jfx.exampleapplication.view.personlogin.PersonLoginView;
import de.saxsys.jfx.exampleapplication.view.personwelcome.PersonWelcomeView;
import de.saxsys.jfx.mvvm.base.view.ViewWithoutViewModel;

/**
 * Main View which creates the necessary subviews, and manages them. Does not
 * need a concrete Viewmodel, so it is typed with the inferface. Have a careful
 * look on the FXML file to see, how to include different views into a
 * MasterView.
 */
public class MainContainerView extends ViewWithoutViewModel {

	@FXML
	// Injection of the login which is declared in the FXML File
	private StackPane loginView; // Value injected by FXMLLoader

	@FXML
	// Inject the Code behind instance of the loginView by using the
	// nameconvention ...Controller
	private PersonLoginView loginViewController;

	@FXML
	// Injection of the login which is declared in the FXML File
	private StackPane welcomeView; // Value injected by FXMLLoader

	@FXML
	// Inject the Code behind instance of the welcomeView by using the
	// nameconvention ...Controller
	private PersonWelcomeView welcomeViewController;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// When the login button of the loginView, the pickedPersonProperty is
		// going to have the index of the selected person
		loginViewController.getViewModel().pickedPersonProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number oldValue, Number newValue) {
						welcomeViewController.getViewModel().setPersonId(
								newValue.intValue());
					}
				});

		welcomeView.setVisible(true);
	}
}
