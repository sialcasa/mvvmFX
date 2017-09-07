package de.saxsys.mvvmfx.examples.scopesexample.ui;

import de.saxsys.mvvmfx.*;
import de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails.DocumentDetailsView;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;



public class MainView implements FxmlView<MainViewModel> {
	@FXML
	public TabPane mainTabPane;

	@InjectViewModel
	private MainViewModel viewModel;

	@InjectContext
	private Context context;

	public void initialize() {

		Tab emptyTabPlaceholder = new Tab("Empty");
		emptyTabPlaceholder.setClosable(false);

		mainTabPane.getTabs().add(emptyTabPlaceholder);

		mainTabPane.getTabs().addListener((ListChangeListener<Tab>) c -> {
			while(c.next()) {

				if(mainTabPane.getTabs().isEmpty()) {
					Platform.runLater(() -> mainTabPane.getTabs().add(emptyTabPlaceholder));
				} else {
					if(mainTabPane.getTabs().contains(emptyTabPlaceholder) && mainTabPane.getTabs().size() > 1) {
						Platform.runLater(() -> mainTabPane.getTabs().remove(emptyTabPlaceholder));
					}
				}
			}
		});



		viewModel.onOpenDocument((title, scopes) -> {

			Parent root = FluentViewLoader.fxmlView(DocumentDetailsView.class)
					.context(context)
					.providedScopes(scopes.toArray(new Scope[]{}))
					.load()
					.getView();

			Tab tab = new Tab();
			tab.setText(title);
			tab.setClosable(true);
			tab.setContent(root);



			mainTabPane.getTabs().add(tab);
		});
	}


}
