package de.saxsys.mvvmfx.examples.scopesexample.ui;

import de.saxsys.mvvmfx.*;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import de.saxsys.mvvmfx.examples.scopesexample.model.DocumentRepository;
import de.saxsys.mvvmfx.examples.scopesexample.ui.documentdetails.DetailsScope;
import de.saxsys.mvvmfx.examples.scopesexample.ui.overview.OverviewScope;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;

import javax.inject.Provider;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@ScopeProvider(scopes = {OverviewScope.class})
public class MainViewModel implements ViewModel {

	public static final String MESSAGE_OPEN_DOCUMENT = "MainViewModel.open_document";
	private final DocumentRepository repository;
	private Provider<DetailsScope> detailsScopeProvider;

	private NotificationCenter notificationCenter = MvvmFX.getNotificationCenter();
	private BiConsumer<String, List<Scope>> openTabConsumer;

	public MainViewModel(DocumentRepository repository, Provider<DetailsScope> detailsScopeProvider) {
		this.repository = repository;
		this.detailsScopeProvider = detailsScopeProvider;

		notificationCenter.subscribe(MESSAGE_OPEN_DOCUMENT, (k,v) -> {
			if(v.length == 1 && v[0] instanceof String) {
				String documentId = (String) v[0];

				openDocument(documentId);
			}
		});
	}

	private void openDocument(String id) {

		Optional<Document> documentOptional = repository.findById(id);

		documentOptional.ifPresent(document -> {
			if(openTabConsumer != null) {
				final DetailsScope detailScope = detailsScopeProvider.get();
				detailScope.documentProperty().setValue(document);

				openTabConsumer.accept(document.getTitle(), Arrays.asList(detailScope));
			}
		});
	}


	public void onOpenDocument(BiConsumer<String, List<Scope>> openTabConsumer) {
		this.openTabConsumer = openTabConsumer;
	}
}
