package de.saxsys.mvvmfx.examples.contacts.ui.about;


import de.saxsys.mvvmfx.FluentViewLoader;

import de.saxsys.mvvmfx.examples.contacts.events.OpenAuthorPageEvent;
import de.saxsys.mvvmfx.examples.contacts.util.DialogHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

import javafx.fxml.FXML;
import javafx.stage.Stage;



public class AboutAuthorView implements FxmlView<AboutAuthorViewModel> {
	
	
	private Parent root;
	

@Singleton
public class AboutAuthorView implements FxmlView<AboutAuthorViewModel> {
	

	@Inject
	private Stage primaryStage;
	
	@InjectViewModel
	private AboutAuthorViewModel viewModel;
	
	
	@FXML
	public void openBlog() {
		viewModel.openBlog();
	}
	
	@FXML
	public void openTwitter() {
		viewModel.openTwitter();
	}
}
