package de.saxsys.mvvmfx.cdi.it;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.stage.Stage;

import javax.inject.Inject;

public class MyView implements FxmlView<MyViewModel> {
	
	@Inject
	Stage primaryStage;
	
}
