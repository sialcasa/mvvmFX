package de.saxsys.mvvmfx.examples.scopesexample;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import de.saxsys.mvvmfx.examples.scopesexample.model.DocumentRepository;
import de.saxsys.mvvmfx.examples.scopesexample.ui.MainView;
import de.saxsys.mvvmfx.examples.scopesexample.ui.overview.OverviewScope;
import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {


	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

		EasyDI easyDI = new EasyDI();
		DocumentRepository repository = easyDI.getInstance(DocumentRepository.class);

		repository.persist(new Document("Test1"));
		repository.persist(new Document("Test2"));
		repository.persist(new Document("Test3"));

		MvvmFX.setCustomDependencyInjector(easyDI::getInstance);

		Parent root = FluentViewLoader.fxmlView(MainView.class)
				.load().getView();


		primaryStage.setScene(new Scene(root, 1200, 800));
		primaryStage.show();
	}
}
