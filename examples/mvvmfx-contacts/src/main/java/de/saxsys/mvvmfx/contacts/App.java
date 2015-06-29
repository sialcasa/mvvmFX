package de.saxsys.mvvmfx.contacts;

import java.util.Locale;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.MvvmFX;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import de.saxsys.mvvmfx.contacts.events.TriggerShutdownEvent;
import de.saxsys.mvvmfx.contacts.model.ContactFactory;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.main.MainView;
import de.saxsys.mvvmfx.contacts.ui.main.MainViewModel;

public class App extends MvvmfxCdiApplication {
	
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	
	public static void main(String... args) {

		Locale.setDefault(Locale.ENGLISH);
		
		launch(args);
	}
	
	
	@Inject
	private ResourceBundle resourceBundle;
	
	@Inject
	private Repository repository;
	
	@Override
	public void initMvvmfx() throws Exception {
		int numberOfContacts = 30;
		for (int i = 0; i < numberOfContacts; i++) {
			repository.save(ContactFactory.createRandomContact());
		}
	}
	
	@Override
	public void startMvvmfx(Stage stage) throws Exception {
		LOG.info("Starting the Application");
		MvvmFX.setGlobalResourceBundle(resourceBundle);
		
		stage.setTitle(resourceBundle.getString("window.title"));
		
		ViewTuple<MainView, MainViewModel> main = FluentViewLoader.fxmlView(MainView.class).load();
		
		
		Scene rootScene = new Scene(main.getView());
		
		rootScene.getStylesheets().add("/contacts.css");
		
		stage.setScene(rootScene);
		stage.show();
	}
	
	/**
	 * The shutdown of the application can be triggered by firing the {@link TriggerShutdownEvent} CDI event.
	 */
	public void triggerShutdown(@Observes TriggerShutdownEvent event) {
		LOG.info("Application will now shut down");
		Platform.exit();
	}
}
