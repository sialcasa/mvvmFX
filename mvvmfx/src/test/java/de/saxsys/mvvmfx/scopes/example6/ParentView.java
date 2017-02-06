package de.saxsys.mvvmfx.scopes.example6;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class ParentView implements FxmlView<ParentViewModel> {

	@FXML
	public TextField newTabContent;
	@FXML
	public TabPane tabPane;

	@InjectViewModel
	private ParentViewModel viewModel;

	public void initialize() {
		newTabContent.textProperty().bindBidirectional(viewModel.inputProperty());

		viewModel.setNewTabAction(scopes -> {
			Parent tabView = FluentViewLoader.fxmlView(TabView.class)
					.providedScopes(scopes)
					.load()
					.getView();

			int nextIndex = tabPane.getTabs().size();
			tabPane.getTabs().add(new Tab("Tab" + nextIndex, tabView));
		});

	}

	public void addTab() {
		viewModel.addTab();
	}
}
