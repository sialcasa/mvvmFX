package de.saxsys.mvvmfx.examples.fx_root_example;

import static org.loadui.testfx.Assertions.verifyThat;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

public class IntegrationTestWithTestFX extends GuiTest {
	
	@Override
	protected Parent getRootNode() {
		return new LabeledTextField();
	}
	
	@Test
	public void testInitialState() {
		verifyThat(".label", (Label l) -> l.getText().equals("default"));
		
		verifyThat(".text-field", (TextField t) -> t.getText().isEmpty());
		
		verifyThat(".button", Button::isDisabled);
	}
	
	@Test
	public void testOk() {
		click(".label").type("Test");
		verifyThat(".button", (button) -> !button.isDisabled());
		
		click(".button");
		
		verifyThat(".label", (Label l) -> l.getText().equals("Test"));
		
		verifyThat(".text-field", (TextField t) -> t.getText().isEmpty());
		verifyThat(".button", Button::isDisabled);
	}
}
