package de.saxsys.mvvmfx.examples.welcome.view.maincontainer;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.examples.welcome.view.personlogin.PersonLoginView;
import de.saxsys.mvvmfx.examples.welcome.view.personwelcome.PersonWelcomeView;
import de.saxsys.mvvmfx.examples.welcome.viewmodel.maincontainer.MainContainerViewModel;
import de.saxsys.mvvmfx.examples.welcome.viewmodel.personwelcome.PersonWelcomeViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import de.saxsys.mvvmfx.utils.viewlist.ViewListCellFactory;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

/**
 * Main View which creates the necessary subviews, and manages them. Does not need a concrete Viewmodel, so it is typed
 * with the inferface. Have a careful look on the FXML file to see, how to include different views into a MasterView.
 */
public class MainContainerView implements FxmlView<MainContainerViewModel>, Initializable {
	
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
	// Notification Center
	private NotificationCenter notificationCenter;
	
	
	@InjectViewModel
	private MainContainerViewModel viewModel;
	
	
	private final Map<Integer, ViewTuple<PersonWelcomeView, PersonWelcomeViewModel>> viewMap = new HashMap<>();
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// Listen for close notifications
		notificationCenter.subscribe("hidePersonWelcome",
				(key, payload) -> {
					int personIdToHide = (int) payload[0];
					viewModel.displayedPersonsProperty().remove(
							new Integer(personIdToHide));
				});
				
		// When the login button of the loginView, the pickedPersonProperty is
		// going to have the index of the selected person
		loginViewController.getViewModel().loggedInPersonIdProperty()
				.addListener((ChangeListener<Number>) (arg0, oldValue, newValue) -> {
					int id = newValue.intValue();
					viewModel.displayedPersonsProperty().add(id);
				});
				
		// Configure List with views
		final ViewListCellFactory<Integer> cellFactory = element -> {
			if (!viewMap.containsKey(element)) {
				ViewTuple<PersonWelcomeView, PersonWelcomeViewModel> loadedViewTuple = FluentViewLoader
						.fxmlView(PersonWelcomeView.class).load();
						
				loadedViewTuple.getViewModel()
						.setPersonId(element);
						
				viewMap.put(element, loadedViewTuple);
			}
			
			return viewMap.get(element);
		};
		personWelcomeListView.setCellFactory(cellFactory);
		
		// Bind list
		personWelcomeListView.itemsProperty().bind(
				viewModel.displayedPersonsProperty());
	}
}
