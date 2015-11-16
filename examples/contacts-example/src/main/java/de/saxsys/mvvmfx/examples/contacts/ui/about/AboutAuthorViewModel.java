package de.saxsys.mvvmfx.examples.contacts.ui.about;

import javax.inject.Inject;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.HostServices;

public class AboutAuthorViewModel implements ViewModel {
	
	@Inject
	private HostServices hostServices;
	
	
	public void openBlog() {
		hostServices.showDocument("http://www.lestard.eu");
	}
	
	public void openTwitter() {
		hostServices.showDocument("https://twitter.com/manuel_mauky");
	}
}
