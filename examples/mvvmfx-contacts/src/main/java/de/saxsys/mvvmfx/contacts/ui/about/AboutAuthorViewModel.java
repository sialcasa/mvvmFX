package de.saxsys.mvvmfx.contacts.ui.about;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.inject.Inject;

public class AboutAuthorViewModel implements ViewModel {
	
	private BooleanProperty dialogOpen = new SimpleBooleanProperty();
	
	@Inject
	private HostServices hostServices;
	
	public BooleanProperty dialogOpenProperty() {
		return dialogOpen;
	}
	
	public void openBlog() {
		hostServices.showDocument("http://www.lestard.eu");
	}
	
	public void openTwitter() {
		hostServices.showDocument("https://twitter.com/manuel_mauky");
	}
	
	public void openDialog() {
		dialogOpen.set(true);
	}
}
