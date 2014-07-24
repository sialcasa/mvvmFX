package de.saxsys.jfx.mvvmfx.helloworld;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import de.saxsys.jfx.mvvm.api.ViewModel;

public class HelloWorldViewModel implements ViewModel {
	
	private StringProperty helloMessage = new SimpleStringProperty("Hello World");
	
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
