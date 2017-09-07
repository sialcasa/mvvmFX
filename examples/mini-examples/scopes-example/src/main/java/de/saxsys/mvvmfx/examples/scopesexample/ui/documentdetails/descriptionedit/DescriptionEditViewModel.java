package de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails.descriptionedit;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails.DetailsScope;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DescriptionEditViewModel implements ViewModel {


	private StringProperty description = new SimpleStringProperty();


	@InjectScope
	private DetailsScope detailsScope;

	public void initialize() {
		detailsScope.documentProperty().addListener((observable, oldValue, newValue) ->
				update(newValue));

		detailsScope.subscribe(DetailsScope.UPDATE, (k,v) -> update(detailsScope.documentProperty().get()));

		update(detailsScope.documentProperty().get());
	}

	private void update(Document document) {
		if(document == null) {
			description.set("");
		} else {
			description.set(document.getDescription());
		}
	}

	public void save() {
		Document document = detailsScope.documentProperty().get();

		document.setDescription(description.get());
		detailsScope.publish(DetailsScope.UPDATE);
	}

	public StringProperty descriptionProperty() {
		return description;
	}

}
