package de.saxsys.jfx.exampleapplication.view.maincontainer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.inject.Inject;

import de.saxsys.jfx.exampleapplication.view.personlogin.PersonLoginView;
import de.saxsys.jfx.exampleapplication.view.personwelcome.PersonWelcomeView;
import de.saxsys.jfx.exampleapplication.viewmodel.personwelcome.PersonWelcomeViewModel;
import de.saxsys.jfx.mvvm.base.view.ViewWithoutViewModel;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationObserver;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;

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
	// Injection of the vbox where the person welcome area will be
	private VBox personInfoVbox;

	@Inject
	// ViewLoder
	private ViewLoader viewLoader;

	@Inject
	// Notification Center
	private NotificationCenter notificationCenter;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Listen for close notifications
		notificationCenter.addObserverForName("closeApplication",
				new NotificationObserver() {
					@Override
					public void receivedNotification(String key,
							Object... objects) {
						Platform.exit();
					}
				});

		// When the login button of the loginView, the pickedPersonProperty is
		// going to have the index of the selected person
		loginViewController.getViewModel().loggedInPersonIdProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number oldValue, Number newValue) {
						ViewTuple<PersonWelcomeViewModel> loadViewTuple = viewLoader
								.loadViewTuple(PersonWelcomeView.class);
						loadViewTuple.getCodeBehind().getViewModel()
								.setPersonId(newValue.intValue());
						personInfoVbox.getChildren().add(
								loadViewTuple.getView());
					}
				});

	}
}
