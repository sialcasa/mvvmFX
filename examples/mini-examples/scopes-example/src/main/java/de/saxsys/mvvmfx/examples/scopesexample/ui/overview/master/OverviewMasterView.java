package de.saxsys.mvvmfx.examples.scopesexample.ui.overview.master;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.scopesexample.model.Document;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class OverviewMasterView implements FxmlView<OverviewMasterViewModel> {

	@FXML
	public ListView<Document> documentList;

	@InjectViewModel
	private OverviewMasterViewModel viewModel;


	public void initialize() {
		documentList.setItems(viewModel.documents());

		viewModel.onSelectionChanged(newSelectedDocument -> {
			if(newSelectedDocument == null) {
				documentList.getSelectionModel().clearSelection();
			} else {
				documentList.getSelectionModel().select(newSelectedDocument);
			}
		});

		documentList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			viewModel.changeSelection(newValue);
		});


		documentList.setOnMouseClicked(event -> {
			if(event.getClickCount() > 1) {
				viewModel.open();
			}
		});
	}

	public void newDocument() {
		viewModel.addNewDocument();
	}

	public void refresh() {
		viewModel.refresh();
	}
}
