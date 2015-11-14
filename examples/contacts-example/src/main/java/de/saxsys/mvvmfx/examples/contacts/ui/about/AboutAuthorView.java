package de.saxsys.mvvmfx.examples.contacts.ui.about;

import javax.inject.Singleton;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;

@Singleton
public class AboutAuthorView implements FxmlView<AboutAuthorViewModel> {
	
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
