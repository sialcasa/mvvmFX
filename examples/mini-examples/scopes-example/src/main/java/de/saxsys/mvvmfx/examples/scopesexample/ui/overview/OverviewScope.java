package de.saxsys.mvvmfx.examples.scopesexample.ui.overview;

import de.saxsys.mvvmfx.Scope;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class OverviewScope implements Scope {

	public static final String UPDATE = "OverviewScope.update";

	private ObjectProperty<Document> selectedDocument = new SimpleObjectProperty<>();

	public ObjectProperty<Document> selectedDocumentProperty() {
		return selectedDocument;
	}

}
