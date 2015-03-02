package de.saxsys.mvvmfx.examples.scopes.ui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.examples.scopes.model.Note;

public class MainView implements FxmlView<MainViewModel> {


	@FXML
	public VBox root;

	@InjectViewModel
	MainViewModel viewModel;

	public void initialize() {

		viewModel.getNotes().forEach(this::createNoteView);

	}

	public void createNoteView(Note note) {
		HBox row = new HBox();

		ScopeViewModel scopeViewModel = new ScopeViewModel();
		scopeViewModel.setNote(note);

		Parent infoView = FluentViewLoader.fxmlView(NoteInfoView.class).scope(scopeViewModel).load().getView();
		Parent textView = FluentViewLoader.fxmlView(NoteTextView.class).scope(scopeViewModel).load().getView();

		row.getChildren().addAll(infoView, textView);

		root.getChildren().add(row);
	}

}
