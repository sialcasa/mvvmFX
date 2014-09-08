package de.saxsys.mvvmfx.contacts;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import de.saxsys.mvvmfx.contacts.events.TriggerShutdownEvent;
import de.saxsys.mvvmfx.contacts.model.ContactFactory;
import de.saxsys.mvvmfx.contacts.model.CountryFactory;
import de.saxsys.mvvmfx.contacts.model.Repository;
import de.saxsys.mvvmfx.contacts.ui.main.MainView;
import de.saxsys.mvvmfx.contacts.ui.main.MainViewModel;


public class App extends MvvmfxCdiApplication{
	
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String...args){
		launch(args);
	}


	/*
	 * Due to the @Produces annotation this resource bundle can be injected in all views.
	 */
	@Produces
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("default",Locale.ENGLISH);

	@Inject
	private Repository repository;
	
	@Override 
	public void init() throws Exception {
		super.init();
		
		repository.save(CountryFactory.createGermany());
		repository.save(CountryFactory.createAustria());
		
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
		repository.save(ContactFactory.createRandomContact());
	}

	@Override 
	public void start(Stage stage) throws Exception {
		LOG.info("Starting the Application");
		
		makePrimaryStageInjectable(stage);
		
		stage.setTitle("mvvmFX Contacts demo");

		
		
		ViewTuple<MainView, MainViewModel> main = FluentViewLoader.fxmlView(MainView.class).resourceBundle(resourceBundle).load();
		
		stage.setScene(new Scene(main.getView()));
		stage.show();
	}
	
	
	public void triggerShutdown(@Observes TriggerShutdownEvent event){
		LOG.info("Application will now shut down");
		Platform.exit();	
	}
}
