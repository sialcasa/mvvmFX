package de.saxsys.mvvmfx.examples.scopes.ui;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.examples.scopes.model.Note;
import de.saxsys.mvvmfx.scopes.ScopeHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainView implements FxmlView<MainViewModel> {


	@FXML
	public VBox root;
	
	@InjectViewModel
	MainViewModel viewModel;
	
	public void initialize(){
		
		viewModel.getNotes().forEach(this::createNoteView);
		
	}
	
	public void createNoteView(Note note){
		HBox row = new HBox();

		ViewTuple<NoteInfoView, NoteInfoViewModel> infoViewTuple = FluentViewLoader.fxmlView(NoteInfoView.class).load();
		Parent infoView = infoViewTuple.getView();

		ViewTuple<NoteTextView, NoteTextViewModel> textViewTuple = FluentViewLoader.fxmlView(NoteTextView.class).load();
		Parent textView = textViewTuple.getView();

		row.getChildren().addAll(infoView, textView);

		root.getChildren().add(row);


		ScopeViewModel scopeViewModel = new ScopeViewModel();
		scopeViewModel.setNote(note);
		ScopeHelper.newScope(scopeViewModel, textViewTuple.getViewModel(), infoViewTuple.getViewModel());
	}
	
}
