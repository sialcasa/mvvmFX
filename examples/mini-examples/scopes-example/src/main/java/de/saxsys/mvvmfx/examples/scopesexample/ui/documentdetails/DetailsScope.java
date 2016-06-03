package de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DetailsScope implements Scope {

	public static final String UPDATE = "DetailsScope.UPDATE";


	private ObjectProperty<Document> document = new SimpleObjectProperty<>();

	public ObjectProperty<Document> documentProperty() {
		return document;
	}

}
