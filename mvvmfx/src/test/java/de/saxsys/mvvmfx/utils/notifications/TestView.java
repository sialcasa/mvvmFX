package de.saxsys.mvvmfx.utils.notifications;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.JavaView;

/**
 * Test view used by {@link TestFxAndNotificationTestHelperTest}.
 * 
 * @author manuel.mauky
 */
public class TestView extends VBox implements JavaView<TestViewModel> {
	
	@InjectViewModel
	private TestViewModel viewModel;
	
	public TestView() {
		Button okButton = new Button("OK");
		okButton.setId("ok_button");
		
		okButton.setOnAction(event -> viewModel.myAction());
		
		this.getChildren().add(okButton);
	}
}
