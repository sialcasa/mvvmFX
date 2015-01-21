package de.saxsys.mvvmfx.examples.scopes.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Note extends Identifiable{
	
	private StringProperty title = new SimpleStringProperty();
	
	private StringProperty text = new SimpleStringProperty();
	
	private ObjectProperty<LocalDate> lastUpdate = new SimpleObjectProperty<>();
	
	public Note(String title){
		this.setTitle(title);
		this.setLastUpdate(LocalDate.now());
	}
	
	public Note(String title, String text){
		this(title);
		this.setText(text);
	}

	public String getTitle() {
		return title.get();
	}

	public StringProperty titleProperty() {
		return title;
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getText() {
		return text.get();
	}

	public StringProperty textProperty() {
		return text;
	}

	public void setText(String text) {
		this.text.set(text);
	}

	public LocalDate getLastUpdate() {
		return lastUpdate.get();
	}

	public ObjectProperty<LocalDate> lastUpdateProperty() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate.set(lastUpdate);
	}
}
 