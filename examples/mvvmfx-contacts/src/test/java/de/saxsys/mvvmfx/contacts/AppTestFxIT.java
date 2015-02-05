package de.saxsys.mvvmfx.contacts;

import javafx.geometry.VerticalDirection;
import javafx.scene.Parent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.Assertions;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.TableViews.containsCell;

public class AppTestFxIT extends FxRobot {
	
	@Before
	public  void setupApp() throws Exception{

		FxToolkit.registerPrimaryStage();
	
		FxToolkit.setupApplication(App.class);
	}
	
	@Test
	public void testAddNewContact(){
		clickOn("#addNewContactButton");
		
		clickOn("#firstnameInput");
		write("luke");
		
		clickOn("#lastnameInput");
		write("skywalker");
		
		clickOn("#emailInput");
		write("luke.skywalker@example.org");
		
		clickOn("#nextButton");
		
		
		clickOn("#okButton");
		
		verifyThat("#masterContactTable", containsCell("luke"));
		verifyThat("#masterContactTable", containsCell("skywalker"));
		verifyThat("#masterContactTable", containsCell("luke.skywalker@example.org"));
	}
}
