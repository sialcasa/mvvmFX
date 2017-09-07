package de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import de.saxsys.mvvmfx.examples.scopesexample.model.DocumentRepository;
import de.saxsys.mvvmfx.examples.scopesexample.ui.overview.OverviewScope;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DocumentDetailsViewModel implements ViewModel {

	private StringProperty title = new SimpleStringProperty();
	private StringProperty description = new SimpleStringProperty();

	private BooleanProperty isSelected = new SimpleBooleanProperty();


	@InjectScope
	private DetailsScope scope;

	@InjectScope
	private OverviewScope overviewScope;

	public void initialize() {
		isSelected.bind(scope.documentProperty().isEqualTo(overviewScope.selectedDocumentProperty()));


		scope.documentProperty().addListener((observable, oldValue, newValue) -> update(newValue));

		scope.subscribe(DetailsScope.UPDATE, (k,v) -> update());

		overviewScope.subscribe(OverviewScope.UPDATE, (k,v) -> {

			Document updatedDocument = overviewScope.selectedDocumentProperty().get();
			Document thisDocument = scope.documentProperty().get();

			if (updatedDocument != null && thisDocument != null && updatedDocument.equals(thisDocument)) {
				update();
			}
		});

		update();
	}

	private void update() {
		update(scope.documentProperty().get());
	}

	private void update(Document document) {
		if(document == null) {
			title.setValue("");
			description.setValue("");
		} else {
			title.setValue(document.getTitle());
			description.setValue(document.getDescription());
		}
	}

	public StringProperty titleProperty() {
		return title;
	}

	public StringProperty descriptionProperty() {
		return description;
	}


	public BooleanProperty isSelectedProperty() {
		return isSelected;
	}
}
