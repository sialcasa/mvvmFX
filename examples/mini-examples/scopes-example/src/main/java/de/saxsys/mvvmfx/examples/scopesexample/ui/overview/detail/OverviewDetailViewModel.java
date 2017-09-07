package de.saxsys.mvvmfx.examples.scopesexample.ui.overview.detail;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import de.saxsys.mvvmfx.examples.scopesexample.model.DocumentRepository;
import de.saxsys.mvvmfx.examples.scopesexample.ui.overview.OverviewScope;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OverviewDetailViewModel implements ViewModel {

	private StringProperty title = new SimpleStringProperty();

	private BooleanProperty editMode = new SimpleBooleanProperty(false);

	@InjectScope
	private OverviewScope scope;

	private final DocumentRepository repository;

	public OverviewDetailViewModel(DocumentRepository repository) {
		this.repository = repository;
	}


	public void initialize() {
		scope.selectedDocumentProperty().addListener((observable, oldValue, newValue) -> refresh());

		scope.subscribe(OverviewScope.UPDATE, (k,v) -> refresh());
	}

	private void refresh() {
		Document document = scope.selectedDocumentProperty().get();
		if(document == null) {
			title.setValue("");
		} else {
			title.setValue(document.getTitle());
		}
	}

	public void edit() {
		editMode.set(!editMode.getValue());
	}


	public void save() {
		Document document = scope.selectedDocumentProperty().get();

		document.setTitle(titleProperty().get());

		repository.persist(document);

		scope.publish(OverviewScope.UPDATE);

		editMode.setValue(false);
	}

	public StringProperty titleProperty() {
		return title;
	}

	public BooleanProperty editModeProperty() {
		return editMode;
	}

}
