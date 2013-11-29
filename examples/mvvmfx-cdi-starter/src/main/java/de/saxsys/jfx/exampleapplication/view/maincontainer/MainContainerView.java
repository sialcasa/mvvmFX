package de.saxsys.jfx.exampleapplication.view.maincontainer;

import de.saxsys.jfx.exampleapplication.view.personlogin.PersonLoginView;
import de.saxsys.jfx.exampleapplication.view.personwelcome.PersonWelcomeView;
import de.saxsys.jfx.exampleapplication.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.jfx.exampleapplication.viewmodel.personwelcome.PersonWelcomeViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.base.view.util.viewlist.ViewListCellFactory;
import de.saxsys.jfx.mvvm.base.viewmodel.ViewModel;
import de.saxsys.jfx.mvvm.notifications.NotificationCenter;
import de.saxsys.jfx.mvvm.notifications.NotificationObserver;
import de.saxsys.jfx.mvvm.viewloader.ViewLoader;
import de.saxsys.jfx.mvvm.viewloader.ViewTuple;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main View which creates the necessary subviews, and manages them. Does not
 * need a concrete Viewmodel, so it is typed with the inferface. Have a careful
 * look on the FXML file to see, how to include different views into a
 * MasterView.
 */
public class MainContainerView extends View<MainContainerViewModel> {

	@FXML
	// Injection of the login which is declared in the FXML File
	private StackPane loginView; // Value injected by FXMLLoader

	@FXML
	// Inject the Code behind instance of the loginView by using the
	// nameconvention ...Controller
	private PersonLoginView loginViewController;

	@FXML
	// Inject the Code behind instance of the ListView
	private ListView<Integer> personWelcomeListView;

	@Inject
	// ViewLoder
	private ViewLoader viewLoader;

	@Inject
	// Notification Center
	private NotificationCenter notificationCenter;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Listen for close notifications
		notificationCenter.addObserverForName("hidePersonWelcome",
				new NotificationObserver() {
					@Override
					public void receivedNotification(String key,
							Object... objects) {
						int personIdToHide = (int) objects[0];
						getViewModel().displayedPersonsProperty().remove(
								new Integer(personIdToHide));
					}
				});

		// When the login button of the loginView, the pickedPersonProperty is
		// going to have the index of the selected person
		loginViewController.getViewModel().loggedInPersonIdProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> arg0,
							Number oldValue, Number newValue) {
						int id = newValue.intValue();
						getViewModel().displayedPersonsProperty().add(id);
					}
				});

		// Configure List with views
		personWelcomeListView.setCellFactory(new ViewListCellFactory<Integer>() {
			@Override
			public ViewTuple<? extends ViewModel> map(Integer element) {
				ViewTuple<PersonWelcomeViewModel> loadViewTuple = viewLoader
						.loadViewTuple(PersonWelcomeView.class);
				loadViewTuple.getCodeBehind().getViewModel()
						.setPersonId(element);
				return loadViewTuple;
			}
		});

		// Bind list
		personWelcomeListView.itemsProperty().bind(
				getViewModel().displayedPersonsProperty());
	}
}
