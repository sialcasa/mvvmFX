package de.saxsys.jfx.mvvmfx.helloworld;

import de.saxsys.jfx.mvvm.api.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HelloWorldViewModel implements ViewModel{

    private StringProperty helloMessage = new SimpleStringProperty("Hello World");

    public StringProperty helloMessage(){
        return helloMessage;
    }

    public String getHelloMessage(){
        return helloMessage.get();
    }

    public void setHelloMessage(String message){
        helloMessage.set(message);
    }

}
