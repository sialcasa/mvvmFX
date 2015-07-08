package de.saxsys.mvvmfx.examples.scopes.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.UUID;

public class Note extends Identifiable{

    private final String id;
	
	private StringProperty title = new SimpleStringProperty();
	
	private StringProperty text = new SimpleStringProperty();
	
    public Note() {
        this("");
    }

	public Note(String title){
        this(title, "");
	}

	public Note(String title, String text){
		this.setTitle(title);
		this.setText(text);
        this.id = UUID.randomUUID().toString();
	}

    public String getId() {
        return id;
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
}
 