package de.saxsys.mvvmfx.examples.contacts;

import de.saxsys.mvvmfx.examples.contacts.App;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TableViewMatchers.hasTableCell;

@Disabled
public class AppTestFxIT extends FxRobot {

	@BeforeEach
	public void setupApp() throws Exception {

		FxToolkit.registerPrimaryStage();

		FxToolkit.setupApplication(App.class);
	}

	@Test
	public void testAddNewContact() {

		clickOn("#addNewContactButton");

		clickOn("#firstnameInput");
		write("luke");

		clickOn("#lastnameInput");
		write("skywalker");

		clickOn("#emailInput");
		write("luke.skywalker@example.org");

		clickOn("#nextButton");

		clickOn("#okButton");

		verifyThat("#masterContactTable", hasTableCell("luke"));
		verifyThat("#masterContactTable", hasTableCell("skywalker"));
		verifyThat("#masterContactTable", hasTableCell("luke.skywalker@example.org"));
	}
}
