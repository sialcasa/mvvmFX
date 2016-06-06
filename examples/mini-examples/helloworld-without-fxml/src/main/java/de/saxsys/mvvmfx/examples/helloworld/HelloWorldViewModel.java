package de.saxsys.mvvmfx.examples.helloworld;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import de.saxsys.mvvmfx.ViewModel;

public class HelloWorldViewModel implements ViewModel {

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
