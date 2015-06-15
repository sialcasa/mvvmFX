package de.saxsys.mvvmfx.utils.notifications;

import static org.assertj.core.api.Assertions.*;

import javafx.scene.Scene;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;

/**
 * The purpose of this test is to check the compatibility of "TestFX" with the {@link NotificationTestHelper}.
 * 
 * @author manuel.mauky
 */
public class TestFxAndNotificationTestHelperTest extends ApplicationTest {
	
	private TestViewModel viewModel;
	
	@Override
	public void start(Stage stage) throws Exception {
		final ViewTuple<TestView, TestViewModel> viewTuple = FluentViewLoader.javaView(TestView.class).load();
		
		viewModel = viewTuple.getViewModel();
		
		stage.setScene(new Scene(viewTuple.getView()));
		stage.show();
	}
	
	@Test
	public void test() {
		
		NotificationTestHelper helper = new NotificationTestHelper();
		viewModel.subscribe("OK", helper);
		
		
		clickOn("#ok_button");
		
		assertThat(helper.numberOfReceivedNotifications()).isEqualTo(1);
	}
}
