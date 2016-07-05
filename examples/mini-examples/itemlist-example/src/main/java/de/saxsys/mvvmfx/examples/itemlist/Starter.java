package de.saxsys.mvvmfx.examples.itemlist;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.easydi.MvvmfxEasyDIApplication;
import de.saxsys.mvvmfx.examples.itemlist.model.IceCreamFlavor;
import de.saxsys.mvvmfx.examples.itemlist.model.IceCreamRepository;
import eu.lestard.easydi.EasyDI;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends MvvmfxEasyDIApplication {

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void initEasyDi(EasyDI context) {
		IceCreamRepository repository = context.getInstance(IceCreamRepository.class);

		repository.persist(new IceCreamFlavor("Vanilla"));
		repository.persist(new IceCreamFlavor("Chocolate"));
		repository.persist(new IceCreamFlavor("Stracciatella"));
		repository.persist(new IceCreamFlavor("Strawberry"));
		repository.persist(new IceCreamFlavor("Hazelnut"));
	}

	@Override
	public void startMvvmfx(Stage stage) {

		Parent parent = FluentViewLoader.fxmlView(ItemListExampleView.class).load().getView();

		stage.setScene(new Scene(parent));
		stage.show();

	}
}
