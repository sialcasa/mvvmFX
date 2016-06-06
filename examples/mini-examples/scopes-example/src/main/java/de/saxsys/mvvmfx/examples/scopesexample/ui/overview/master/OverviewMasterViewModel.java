package de.saxsys.mvvmfx.examples.scopesexample.ui.overview.master;

import de.saxsys.mvvmfx.InjectScope;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import de.saxsys.mvvmfx.examples.scopesexample.model.DocumentRepository;
import de.saxsys.mvvmfx.examples.scopesexample.ui.MainViewModel;
import de.saxsys.mvvmfx.examples.scopesexample.ui.overview.OverviewScope;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.function.Consumer;

public class OverviewMasterViewModel implements ViewModel {


	private ObservableList<Document> documents = FXCollections.observableArrayList();
	private ObjectProperty<Document> selectedDocument = new SimpleObjectProperty<>();

	private Consumer<Document> selectionChangedConsumer;

	@InjectScope
	private OverviewScope scope;

	private DocumentRepository repository;

	private NotificationCenter notificationCenter = MvvmFX.getNotificationCenter();

	public OverviewMasterViewModel(DocumentRepository repository) {
		this.repository = repository;
	}

	public void initialize() {

		scope.selectedDocumentProperty().bindBidirectional(selectedDocument);


		scope.subscribe(OverviewScope.UPDATE, (k,v) -> {
			Collection<Document> all = repository.findAll();


			final Document previouslySelectedDocument = selectedDocument.get();

			documents.clear();
			documents.addAll(all);

			if(documents.contains(previouslySelectedDocument)) {
				if(selectionChangedConsumer != null) {
					selectionChangedConsumer.accept(previouslySelectedDocument);
					selectedDocument.setValue(previouslySelectedDocument);
				}
			} else {
				selectedDocument.setValue(null);
			}

		});

		refresh();
	}

	public void onSelectionChanged(Consumer<Document> consumer) {
		selectionChangedConsumer = consumer;
	}

	public void changeSelection(Document newSelectedDocument) {
		selectedDocument.setValue(newSelectedDocument);
	}

	public void open() {
		if(selectedDocument.get() != null) {
			notificationCenter.publish(MainViewModel.MESSAGE_OPEN_DOCUMENT, selectedDocument.get().getId());
		}
	}

	public void refresh() {
		scope.publish(OverviewScope.UPDATE);
	}

	public void addNewDocument() {
		Document document = new Document();
		document.setTitle("unnamed");

		repository.persist(document);

		refresh();
	}

	public ObservableList<Document> documents() {
		return documents;
	}

	public ObjectProperty<Document> selectedDocumentProperty() {
		return selectedDocument;
	}

}
