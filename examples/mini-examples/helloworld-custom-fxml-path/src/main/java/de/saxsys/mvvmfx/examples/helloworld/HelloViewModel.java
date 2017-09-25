package de.saxsys.mvvmfx.examples.helloworld;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HelloViewModel implements ViewModel {

	private final StringProperty helloMessage = new SimpleStringProperty("Hello World");

	public StringProperty helloMessage() {
		return helloMessage;
	}

	public String getHelloMessage() {
		return helloMessage.get();
	}

	public void setHelloMessage(String message) {
		helloMessage.set(message);
	}

}
