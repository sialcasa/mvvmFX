package de.saxsys.mvvmfx.guice.it;

import javafx.stage.Stage;

import javax.inject.Inject;

import de.saxsys.mvvmfx.FxmlView;

public class MyView implements FxmlView<MyViewModel> {
	
	@Inject
	Stage primaryStage;
	
}
