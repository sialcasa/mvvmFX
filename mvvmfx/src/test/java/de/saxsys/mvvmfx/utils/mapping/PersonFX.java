package de.saxsys.mvvmfx.utils.mapping;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonFX {
	
	private StringProperty name = new SimpleStringProperty();
	
	private IntegerProperty age = new SimpleIntegerProperty();
	
	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
	
	public int getAge() {
		return age.get();
	}
	
	public IntegerProperty ageProperty() {
		return age;
	}
	
	public void setAge(int age) {
		this.age.set(age);
	}
}
