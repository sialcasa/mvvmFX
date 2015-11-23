package de.saxsys.mvvmfx.examples.contacts.ui.menu;

import javax.inject.Inject;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.contacts.ui.about.AboutView;
import de.saxsys.mvvmfx.examples.contacts.util.DialogHelper;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuView implements FxmlView<MenuViewModel> {
	
	@FXML
	private MenuItem removeMenuItem;
	
	@InjectViewModel
	private MenuViewModel viewModel;
	
	@Inject
	private Stage primaryStage;
	
	public void initialize() {
		removeMenuItem.disableProperty().bind(viewModel.removeItemDisabledProperty());
	}
	
	@FXML
	public void close() {
		viewModel.closeAction();
	}
	
	@FXML
	public void remove() {
		viewModel.removeAction();
	}
	
	@FXML
	public void about() {
		Parent view = FluentViewLoader.fxmlView(AboutView.class)
				.load().getView();
		DialogHelper.showDialog(view, primaryStage, "/contacts.css");
	}
}
